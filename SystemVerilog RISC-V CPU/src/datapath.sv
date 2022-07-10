import pipeline_pkg::*;
import riscv_package::*;

module datapath
  #(
    parameter AW = 32,
    parameter DW = 32
  )
  (
    input logic          clk,
    input logic          rst_n,
    input riscv_inst32_t inst,
    
    controller_if.in     ctrl,
    output logic [AW-1:0]      pc,
    memory_if.master     dmem_if
  
  );
  

  if_id_reg_t   if_id_reg_d;
  if_id_reg_t   if_id_reg_q; 
 
  id_ex_reg_t   id_ex_reg_d;
  id_ex_reg_t   id_ex_reg_q;

  ex_mem_reg_t  ex_mem_reg_d;
  ex_mem_reg_t  ex_mem_reg_q;

  mem_wb_reg_t  mem_wb_reg_d;
  mem_wb_reg_t  mem_wb_reg_q;

  control    ctrl_signal;

  logic [AW-1:0] pc_next;
  logic [DW-1:0] aluop1, aluop2, alu_result;
  logic [DW-1:0] regf_dout1, regf_dout2;
  logic [4:0] regf_wr_addr;
  logic [DW-1:0] regf_wr_data;
  logic [DW-1:0] imm_val;
  logic          alu_zero;

//forwarding unit 
  logic [1:0] forwardA;
  logic [1:0] forwardB;
  logic [DW-1:0] Aresult;
  logic [DW-1:0] Bresult;

//hazard detection unit
  logic stall;

// IF/ID varialbe
  logic [4:0] r_addr1;
  logic [4:0] r_addr2;
  logic          en;
// ID/EX variable
  logic [DW-1:0] if_id_pc;
  logic [DW-1:0] id_r_data1;
  logic [DW-1:0] id_r_data2;
  logic [1:0] forwardA2;
  logic [1:0] forwardB2;
  logic [DW-1:0] comp_result;
  logic [DW-1:0]  jal_addr;
  logic       jal_signal;
  logic [DW-1:0] jalr_addr;
  logic       jalr_signal;
// EX/MEM variable
  logic [2:0] ex_funct3;
  logic [6:0] ex_funct7;
  logic [2:0] mem_readdatasel;
  logic [1:0] mem_writedatasel;
  logic [8:0] branch_addr;
// MEM variables
  logic mem_alu_zero;
  logic regf_wr_en;
  logic PCSrc;
  //assign PCSrc = 0;
  assign en = 1;
  control ctrl_signal_d;
/************************** IF cycle **************************/

    assign pc_next = (PCSrc) ? (branch_addr)  : (jal_signal)? (jal_addr):(jalr_signal)? jalr_addr :(stall || id_ex_reg_d.jalr_mode )? pc : (pc + 1) ;



  
    assign if_id_reg_d.instr = inst;
    assign if_id_reg_d.pc_next = pc_next;
  

    assign ctrl_signal_d.reg_write = ctrl.reg_write;
    assign ctrl_signal_d.mem2reg = ctrl.mem2reg;
    assign ctrl_signal_d.mem_write = ctrl.mem_write;
    assign ctrl_signal_d.branch = ctrl.branch;
    assign ctrl_signal_d.jal_mode = ctrl.jal_mode;
    assign ctrl_signal_d.jalr_mode = ctrl.jalr_mode;
    assign ctrl_signal_d.aluop = ctrl.aluop;
    assign ctrl_signal_d.alu_src = ctrl.alu_src;
    assign ctrl_signal_d.lui_mode = ctrl.lui_mode;
    assign ctrl_signal_d.mem_read = ctrl.mem_read;

  program_counter
    #(.AW(AW))
    pc_inst
    ( .clk    ( clk   ),
       .rst_n  ( rst_n ),
       .enable ( 1'b1  ),
       .din    ( pc_next ),
       .pc     ( pc      )
     );
  

// IF/ID flip_flop
  always_ff @(posedge clk)begin
   if(~rst_n)
    begin
     if_id_reg_q <= 32'h0;
     ctrl_signal <= 0;
    end
   else if (en && ~stall)
    begin
      if_id_reg_q.instr <= if_id_reg_d.instr;
      if_id_reg_q.pc_next <= if_id_reg_d.pc_next;
      ctrl_signal.reg_write <=  ctrl_signal_d.reg_write;
      ctrl_signal.mem2reg <= ctrl_signal_d.mem2reg;
      ctrl_signal.mem_write <= ctrl_signal_d.mem_write;
      ctrl_signal.branch <= ctrl_signal_d.branch;
      ctrl_signal.jal_mode <= ctrl_signal_d.jal_mode;
      ctrl_signal.jalr_mode <= ctrl_signal_d.jalr_mode;
      ctrl_signal.aluop <= ctrl_signal_d.aluop;
      ctrl_signal.alu_src <= ctrl_signal_d.alu_src;
      ctrl_signal.lui_mode <= ctrl_signal_d.lui_mode;
      ctrl_signal.mem_read <= ctrl_signal_d.mem_read;
    
    
    end
   end
  

/************************ ID cycle ***************************/
   
    hazard_detection hazDetect (id_ex_reg_q.w_addr, r_addr1, r_addr2, id_ex_reg_q.mem_read, stall);


  assign  id_ex_reg_d.r_data1 =stall  ? 'h0 : id_r_data1;
  assign  id_ex_reg_d.r_data2= stall ? 'h0 : id_r_data2;
  assign  id_ex_reg_d.imm_val = stall ? 'h0 : imm_val;

always_comb
begin
if (stall) begin
    //control signals
    id_ex_reg_d.alu_src <= 0;
    id_ex_reg_d.reg_write <=  0;
    id_ex_reg_d.mem2reg <= 0;
    id_ex_reg_d.mem_write <= 0;
    id_ex_reg_d.branch <=  0;
    id_ex_reg_d.jal_mode <= 0;
    id_ex_reg_d.jalr_mode <= 0;
    id_ex_reg_d.aluop <= 0;
    id_ex_reg_d.lui_mode <= 0;
    id_ex_reg_d.mem_read <= 0;

end else begin
    //control signals
    id_ex_reg_d.alu_src <= ctrl_signal.alu_src;
    id_ex_reg_d.reg_write <=  ctrl_signal.reg_write;
    id_ex_reg_d.mem2reg <= ctrl_signal.mem2reg;
    id_ex_reg_d.mem_write <= ctrl_signal.mem_write;
    id_ex_reg_d.branch <=  ctrl_signal.branch;
    id_ex_reg_d.jal_mode <= ctrl_signal.jal_mode;
    id_ex_reg_d.jalr_mode <= ctrl_signal.jalr_mode;
    id_ex_reg_d.aluop <= ctrl_signal.aluop;
    id_ex_reg_d.lui_mode <= ctrl_signal.lui_mode;
    id_ex_reg_d.mem_read <= ctrl_signal.mem_read;
end
end


    assign r_addr1 = if_id_reg_q.instr[19:15];
    assign r_addr2 =  if_id_reg_q.instr[24:20];
    assign id_ex_reg_d.funct3 = if_id_reg_q.instr[14:12];
    assign id_ex_reg_d.funct7 = if_id_reg_q.instr[31:25];
    assign id_ex_reg_d.w_addr = if_id_reg_q.instr[11:7];

  always @(posedge clk)begin
    if (stall) begin
	id_ex_reg_d.pc<= pc;
end else begin
	id_ex_reg_d.pc<=if_id_reg_q.pc_next;
    end
end 

  
  
  
 
    //calculating branch/jal/jalr addresses
    assign PCSrc = (id_ex_reg_d.branch & (alu_zero));
    assign branch_addr = id_ex_reg_d.pc + (id_ex_reg_d.imm_val>>2);
    assign jal_addr = id_ex_reg_d.pc + (id_ex_reg_d.imm_val>>2);
    assign jal_signal = ex_mem_reg_d.jal_mode;
    assign jalr_addr = ((id_ex_reg_q.r_data1 + id_ex_reg_q.imm_val)&'hFFFFFFFE);
    assign jalr_signal = ex_mem_reg_d.jalr_mode; 
    //comparator for jump instructions
    comparator
    comp
    (
      .operand_a  (id_r_data1),
      .operand_b  (id_r_data2),
      .zero       (alu_zero),
      .result1    (comp_result),
      .funct3     (id_ex_reg_d.funct3 ),
      .funct7     (id_ex_reg_d.funct7 ),
      .aluop      (id_ex_reg_d.aluop )
    );
    
  


    register_file
     #( .AW(5), .DW(DW))
     regf_inst
     (
       .clk      ( clk            ),
       .rst_n    ( rst_n          ),
       .rd_addr1 ( r_addr1 ),     
       .rd_addr2 ( r_addr2 ),     
       .rd_data1 ( id_r_data1   ),
       .rd_data2 ( id_r_data2   ),
       .wr_en    ( regf_wr_en     ),
       .wr_addr  ( regf_wr_addr   ),
       .wr_data  ( regf_wr_data   )
     );
     
     immediate_generator
     #( .DW(DW) )
     immgen_inst
     (
       .inst ( if_id_reg_q.instr ),
       .imm  ( imm_val )
     );




// ID/EX  flip_flop
  always_ff @(posedge clk)begin
   if(~rst_n)
      id_ex_reg_q <= 32'h0;
   else if (en)
    begin
    id_ex_reg_q.r_data1 <= id_ex_reg_d.r_data1;
    id_ex_reg_q.r_data2 <= id_ex_reg_d.r_data2;
    id_ex_reg_q.pc      <= id_ex_reg_d.pc;
    id_ex_reg_q.imm_val <= id_ex_reg_d.imm_val;
    id_ex_reg_q.funct3 <= id_ex_reg_d.funct3;
    id_ex_reg_q.funct7 <= id_ex_reg_d.funct7;
    id_ex_reg_q.w_addr <= id_ex_reg_d.w_addr;
    //control signals
    id_ex_reg_q.reg_write  <= id_ex_reg_d.reg_write;
    id_ex_reg_q.mem2reg    <= id_ex_reg_d.mem2reg;
    id_ex_reg_q.mem_write  <= id_ex_reg_d.mem_write;
    id_ex_reg_q.branch    <= id_ex_reg_d.branch;
    id_ex_reg_q.jal_mode  <= id_ex_reg_d.jal_mode;
    id_ex_reg_q.jalr_mode <= id_ex_reg_d.jalr_mode;
    id_ex_reg_q.aluop   <=  id_ex_reg_d.aluop;
    id_ex_reg_q.alu_src <= id_ex_reg_d.alu_src;
    id_ex_reg_q.lui_mode <= id_ex_reg_d.lui_mode;
    id_ex_reg_q.mem_read <= id_ex_reg_d.mem_read;
    forwardA2     <= forwardA;
    forwardB2     <= forwardB;
    
    end
  end

    forwarding_unit FrwdUnit (ex_mem_reg_d.reg_write, mem_wb_reg_d.reg_write, r_addr1, r_addr2, ex_mem_reg_d.w_addr, mem_wb_reg_d.w_addr,forwardA, forwardB);

    assign Aresult = (forwardA2 == 2'h1) ? regf_wr_data : (forwardA2 == 2'h2) ? ex_mem_reg_q.alu_result : id_ex_reg_q.r_data1;
    assign Bresult = (forwardB2 == 2'h1) ? regf_wr_data : (forwardB2 == 2'h2) ? ex_mem_reg_q.alu_result : id_ex_reg_q.r_data2;



/********************** EX cycle ****************************/
 
    //hazard_detection hazDetect (id_ex_reg_q.w_addr, r_addr1, r_addr2, id_ex_reg_q.mem_read, stall);






    assign  ex_mem_reg_d.alu_result = alu_result;
    assign ex_mem_reg_d.r_data2 = Bresult;
    assign  ex_mem_reg_d.pc_next = id_ex_reg_q.pc; 
    assign ex_mem_reg_d.w_addr = id_ex_reg_q.w_addr;
    assign ex_mem_reg_d.zero =  alu_zero ;
  

    assign  ex_funct3 = id_ex_reg_q.funct3;
    assign  ex_funct7 = id_ex_reg_q.funct7;
    assign  aluop1 = Aresult;
    assign  aluop2 = ~(id_ex_reg_q.alu_src) ?  Bresult : (mem_wb_reg_q.mem2reg == 2'b10)?          (id_ex_reg_q.pc + id_ex_reg_q.imm_val) :id_ex_reg_q.imm_val;
  

  //control signals
  // wb


 
  assign  ex_mem_reg_d.reg_write = id_ex_reg_q.reg_write;  
  assign  ex_mem_reg_d.mem2reg = id_ex_reg_q.mem2reg;
  // mem
  assign  ex_mem_reg_d.mem_write = id_ex_reg_q.mem_write;  
  assign  ex_mem_reg_d.branch =  id_ex_reg_q.branch;
  assign  ex_mem_reg_d.jal_mode =  id_ex_reg_q.jal_mode;
  assign  ex_mem_reg_d.jalr_mode = id_ex_reg_q.jalr_mode;
  assign  ex_mem_reg_d.readdatasel = mem_readdatasel;
  assign  ex_mem_reg_d.writedatasel = mem_writedatasel;
  assign  ex_mem_reg_d.imm_val  = id_ex_reg_q.imm_val;
     
 




   alu_operation_e  operation;
   logic       cont_beq;
   logic       cont_bnq;
   logic       cont_blt;
   logic       cont_bgt;
   logic [2:0] readdatasel;
   logic [1:0] writedatasel;
     
   alu_controller aluc_inst(
     .*,
     .aluop  ( id_ex_reg_q.aluop    ),
     .funct3 ( ex_funct3 ),     
     .funct7 ( ex_funct7 ),     
     .readdatasel ( mem_readdatasel ),
     .writedatasel ( mem_writedatasel )
   );
    
   alu
   #()
   alu_inst
   (
     .operand_a ( aluop1    ),
     .operand_b ( aluop2    ),
     .operation ( operation ),
     .result    ( alu_result )
 
   );


// EX/MEM flip_flop
  always_ff@(posedge clk)begin
   if(~rst_n)
      ex_mem_reg_q <= 32'h0;
   else if (en)
    begin
    ex_mem_reg_q.pc_next <=ex_mem_reg_d.pc_next;
    ex_mem_reg_q.r_data2 <=ex_mem_reg_d.r_data2;
    ex_mem_reg_q.alu_result <=ex_mem_reg_d.alu_result;
    ex_mem_reg_q.w_addr  <= ex_mem_reg_d.w_addr;
    ex_mem_reg_q.zero  <= ex_mem_reg_d.zero;
    ex_mem_reg_q.imm_val <= ex_mem_reg_d.imm_val;
    //signals
    ex_mem_reg_q.reg_write <= ex_mem_reg_d.reg_write;
    ex_mem_reg_q.mem2reg <= ex_mem_reg_d.mem2reg;
    ex_mem_reg_q.mem_write <= ex_mem_reg_d.mem_write;
    ex_mem_reg_q.branch <= ex_mem_reg_d.branch;
    ex_mem_reg_q.jal_mode <= ex_mem_reg_d.jal_mode;
    ex_mem_reg_q.jalr_mode <= ex_mem_reg_d.jalr_mode;
    ex_mem_reg_q.readdatasel <= ex_mem_reg_d.readdatasel;
    ex_mem_reg_q.writedatasel <= ex_mem_reg_d.writedatasel;
    end
  end



/*********************** MEM cycle ******************************/

  assign dmem_if.addr  = ex_mem_reg_q.alu_result;
  assign dmem_if.wr    = ex_mem_reg_q.mem_write; 
  assign dmem_if.wdata = ex_mem_reg_q.r_data2;
  assign dmem_if.writeEnable = ex_mem_reg_q.alu_result[1:0];
  assign dmem_if.writedatasel = ex_mem_reg_q.writedatasel;
  assign dmem_if.readdatasel = ex_mem_reg_q.readdatasel;

  assign mem_wb_reg_d.r_data2 = dmem_if.rdata;
  assign mem_wb_reg_d.w_addr = ex_mem_reg_q.w_addr;
  assign mem_wb_reg_d.alu_result = ex_mem_reg_q.alu_result;
  assign mem_wb_reg_d.pc_next = ex_mem_reg_q.pc_next;
  //contrl signals
  //wb
  assign mem_wb_reg_d.reg_write = ex_mem_reg_q.reg_write;
  assign mem_wb_reg_d.mem2reg = ex_mem_reg_q.mem2reg;
  assign mem_wb_reg_d.imm_val = ex_mem_reg_q.imm_val;

// MEM/WB flip_flop
  always_ff@(posedge clk)begin
   if(~rst_n)
      mem_wb_reg_q <= 32'h0;
   else if(en)
    begin
    mem_wb_reg_q.alu_result <= mem_wb_reg_d.alu_result;
    mem_wb_reg_q.r_data2 <= mem_wb_reg_d.r_data2;
    mem_wb_reg_q.w_addr <= mem_wb_reg_d.w_addr;
    //control signals
    mem_wb_reg_q.reg_write <= mem_wb_reg_d.reg_write; 
    mem_wb_reg_q.mem2reg <= mem_wb_reg_d.mem2reg;
    mem_wb_reg_q.pc_next <= mem_wb_reg_d.pc_next;
    mem_wb_reg_q.imm_val <= mem_wb_reg_d.imm_val;
    end
 end

/*********************** WB cycle *****************************/

 
  assign regf_wr_addr = mem_wb_reg_q.w_addr;
  assign regf_wr_data = ( mem_wb_reg_q.mem2reg== 2'b11) ? (mem_wb_reg_q.pc_next + 1) : (mem_wb_reg_q.mem2reg == 2'b10) ? (mem_wb_reg_q.pc_next + mem_wb_reg_d.imm_val) : (mem_wb_reg_q.mem2reg != 2'b01) ? mem_wb_reg_q.alu_result : mem_wb_reg_q.r_data2;
  assign regf_wr_en = mem_wb_reg_q.reg_write;

 


 
endmodule:datapath
