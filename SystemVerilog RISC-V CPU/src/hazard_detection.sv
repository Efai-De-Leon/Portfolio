module hazard_detection(
	input logic [4:0] id_ex_registerRd,
	input logic [4:0] if_id_registerRs1,
	input logic [4:0] if_id_registerRs2,
	input logic id_ex_memRead, 
	output logic stall);

	assign stall = (id_ex_memRead) && ((id_ex_registerRd == if_id_registerRs1) || (id_ex_registerRd == if_id_registerRs2));
endmodule
