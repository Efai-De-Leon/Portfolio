module forwarding_unit
(
	input logic ex_mem_reg_write, //EX/MEM.RegWrite
	input logic mem_wb_reg_write, //MEM/WB.RegWrite
        input logic [4:0] id_ex_raddr1, //ID/EX.RegisterRs1
        input logic [4:0] id_ex_raddr2, //ID/EX.RegisterRs2
	input logic [4:0] ex_mem_waddr, //EX/MEM.RegisterRd
	input logic [4:0] mem_wb_waddr, //MEM/WB.RegisterRd
	output logic[1:0] ForwardA,
	output logic [1:0] ForwardB
);
//safe case 
//      assign ForwardA = ((ex_mem_reg_write) && (ex_mem_waddr != 0) && ex_mem_waddr == id_ex_raddr1) ? 2'b10 : ((mem_wb_reg_write) && (mem_wb_waddr !=0) && (mem_wb_waddr == id_ex_raddr1)) ? 2'b01: 2'b00;  

	assign ForwardA = ((ex_mem_reg_write) && (ex_mem_waddr != 0) && ex_mem_waddr == id_ex_raddr1) ? 2'b10 : ((mem_wb_reg_write) && (mem_wb_waddr !=0) && !((ex_mem_reg_write) && (ex_mem_waddr!=0) && (ex_mem_waddr == id_ex_raddr1)) && (mem_wb_waddr == id_ex_raddr1)) ? 2'b01 : 2'b00;

//safe case 
//	assign ForwardB = ((ex_mem_reg_write) && (ex_mem_waddr!=0) && (ex_mem_waddr == id_ex_raddr2) ? 2'b10 : ((mem_wb_reg_write) && (mem_wb_waddr != 0) && (mem_wb_waddr == id_ex_raddr2)) ? 2'b01 : 2'b00; 

        assign ForwardB = ((ex_mem_reg_write) && (ex_mem_waddr!=0) && (ex_mem_waddr == id_ex_raddr2)) ? 2'b10 : ((mem_wb_reg_write) && (mem_wb_waddr != 0) && !((ex_mem_reg_write) && (ex_mem_waddr!=0) && (ex_mem_waddr == id_ex_raddr2)) && (mem_wb_waddr == id_ex_raddr2)) ? 2'b01 : 2'b00;

endmodule
