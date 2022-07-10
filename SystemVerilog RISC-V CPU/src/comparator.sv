import riscv_package::*;
module comparator
 #( parameter DW = 32
 ) 
 (

  input  [1:0]  aluop,
  input  [2:0]  funct3,
  input  [6:0]  funct7,
  input  logic  [DW-1:0] operand_a,
  input  logic  [DW-1:0] operand_b,
  output logic zero,
  output logic [DW-1:0] result1
  
);

//  logic [DW-1:0] result;

alu_operation_e operation;
logic beq;
logic bne;
logic blt;
logic bge;



assign operation[0]= (((aluop == 2'b10)  && ((funct3==3'b110) || (funct3==3'b001)         ||      (funct3==3'b100) || (funct7 == 7'b0000000 && (funct3==3'b101))                      ||                      (funct3==3'b011))) ||  ( (aluop == 2'b01 ) && ((funct3==3'b110)     || (funct3==3'b111) ) )     );

assign operation[1]= (   ((aluop ==2'b10) &&                                              ((funct3==3'b000)||(funct3==3'b001)||(funct3==3'b100) ) ) ||
 ((aluop ==2'b00) && ((funct3==3'b000) || (funct3==3'b001) || (funct3==3'b100) ||            (funct3==3'b101) || (funct3==3'b010)) ) ||
 ((aluop==2'b01) && ((funct3==3'b000) || (funct3==3'b001) || (funct3==3'b111) ||            (funct3==3'b101))) ||
 (aluop==2'b11));

 assign operation[2]=  (    ( (aluop ==2'b10) &&   (( funct7 == 7'b0100000  &&             (funct3==3'b000)) || (funct3==3'b100) ||(funct3==3'b101)        )        ) ||
     (    (aluop ==2'b01) &&    (    (funct3==3'b000) || (funct3==3'b001)    )  ));


  assign operation[3]= (( (aluop ==2'b10) && ((funct3==3'b010) || (funct3==3'b011)  )       ) ||
      ( (aluop ==2'b01) && ( (funct3==3'b100) || (funct3==3'b101) || (funct3==3'b110)       || (funct3==3'b111) )));


assign beq = ( (aluop ==2'b01) && (funct3==3'b000) );
assign bne = ( (aluop ==2'b01) && (funct3==3'b001) );
assign blt = ( (aluop ==2'b01) && ( (funct3==3'b100) || (funct3==3'b110) ) );
assign bge = ( (aluop ==2'b01) && ( (funct3==3'b101) || (funct3==3'b111) ) );



always_comb
begin
  result1 = 'd0;
  case(operation)
    ALU_SUB:
      result1 = $signed(operand_a) - $signed(operand_b);
    ALU_SLT:
      result1 = $signed(operand_a) < $signed(operand_b);
    ALU_SLTU:
      result1 = operand_a < operand_b;
    ALU_SGE:
      result1 = operand_a >= operand_b;
    ALU_SGEU:
      result1 = $unsigned(operand_a) >= $unsigned(operand_b);
    default:
      result1 = 'b0;
    endcase
    if(bne)
      zero = result1 == 'b0 ? 'h0 : 'h1;
    else if(beq)
      zero = result1 == 'b0 ? 'h1 : 'h0;
    else if(blt)
      zero = result1 ==  'b1 ? 'h1 : 'h0;
    else if(bge)
      zero = result1 == 'b1 ? 'h1 : 'h0;
    else
      zero = 'h0;

  end
endmodule:comparator

