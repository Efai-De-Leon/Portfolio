//import riscv_package::*;
package pipeline_pkg;

typedef struct packed{
  logic [31:0] pc_next;
  logic [31:0]  instr;



}if_id_reg_t;

typedef struct packed{
  logic [31:0]  r_data1;
  logic [31:0]  r_data2;  
  logic [31:0]       pc;
  logic [31:0]   imm_val;
  logic [2:0]  funct3;
  logic [6:0]  funct7;
  logic [4:0] w_addr;


//  control signals
//  wb signals
  logic reg_write;
  logic [1:0] mem2reg;
//  mem signals
  logic mem_write;
  logic branch;
  logic jal_mode;
  logic jalr_mode;
//  ex signals
  logic [1:0] aluop;
  logic       alu_src;
  logic        lui_mode;
  logic       mem_read;

}id_ex_reg_t;


typedef struct packed{
  logic [31:0]   pc_next;
  logic [1:0]    zero;
  logic [31:0]   alu_result;
  logic [31:0]   r_data2;
  logic [4:0]   w_addr;
  logic [31:0]   imm_val;
  // wb signals
  logic reg_write;
  logic [1:0] mem2reg;
  //  mem signals
  logic mem_write;
  logic branch;
  logic jal_mode;
  logic jalr_mode;
  logic [2:0] readdatasel;
  logic [1:0] writedatasel;
}ex_mem_reg_t;

  
typedef struct packed{
  logic  [31:0] r_data2;
  logic  [31:0] alu_result;
  logic  [4:0] w_addr;
  logic  [31:0] pc_next;
  // wb signals 
  logic reg_write;
  logic [1:0] mem2reg;
  logic [31:0]   imm_val;

}mem_wb_reg_t;


typedef struct packed{
  //  control signals
  //  wb signals
  logic reg_write;
  logic [1:0] mem2reg;
  //  mem signals
  logic mem_write;
  logic branch;
  logic jal_mode;
  logic jalr_mode;
  //  ex signals
  logic [1:0] aluop;
  logic       alu_src;
  logic        lui_mode;
  logic       mem_read;
}control;
//typedef union packed {
 // if_id_reg_t   if_id;
 // id_ex_reg_t   id_ex;
 // ex_mem_reg_t  ex_mem;
 // mem_wb_reg_t  mem_wb;

//}pipeline;


endpackage : pipeline_pkg
