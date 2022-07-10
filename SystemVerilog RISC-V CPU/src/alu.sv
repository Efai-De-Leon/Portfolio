
import riscv_package::*;
module alu
  #( parameter DW = 32
  )
  (
    input  logic  [DW-1:0] operand_a,
    input  logic  [DW-1:0] operand_b,
    input  alu_operation_e operation,
  //  input  logic           bne,
  //  input  logic           beq,
 //   input  logic           blt,
  //  input  logic           bge,
    output logic  [DW-1:0] result
 //   output logic           branch
  );

  wire [4:0]       shiftamount;
  assign shiftamount = operand_b[4:0];
  always_comb
  begin
    result = 'd0;
    case(operation)
      ALU_AND:
        result = operand_a & operand_b;
      ALU_OR :
        result = operand_a | operand_b;
      ALU_ADD:
        result = operand_a + operand_b;
      ALU_SUB:
        result = $signed(operand_a) - $signed(operand_b);
      ALU_SLT:
        result = $signed(operand_a) < $signed(operand_b);
      ALU_SLTU:
        result = operand_a < operand_b;
      ALU_XOR:
        result = operand_a ^ operand_b;
      ALU_SLL:
        result = operand_a << operand_b;
      ALU_SRL:
        result = operand_a >> operand_b;
      ALU_SRA:
        result = $signed(operand_a)>>>shiftamount;	
      ALU_SGE:
        result = operand_a >= operand_b ;
      ALU_SGEU:
        result = $unsigned(operand_a) >= $unsigned(operand_b);
      default:
        result = 'b0;
    endcase
  /*  if(bne)
      branch = result == 'b0 ? 'h0 : 'h1;
    else if(beq)
      branch = result == 'b0 ? 'h1 : 'h0;
    else if(blt)
      branch = result ==  'b1 ? 'h1 : 'h0;
    else if(bge)
      branch = result == 'b1 ? 'h1 : 'h0;
    else
      branch = 'h0;
  */
  end
endmodule:alu
