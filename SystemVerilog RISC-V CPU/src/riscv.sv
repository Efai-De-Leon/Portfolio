import pipeline_pkg::*;
import riscv_package::*;

module riscv
  #(
    CORES = 1,
    DATA_WIDTH = 32,
    ADDR_WIDTH = 32
  )
  (
    input logic clk,
    input logic rst_n,
  // Register Interface
    input logic [31:0] addr,
    input logic [31:0] data,
    input logic        valid
    
  // Interconnect Interface
  // Debug Interface
  );

  /* local definitions*/
  logic [ADDR_WIDTH-1:0] pc;
  logic [ADDR_WIDTH-1:0] imem_addr;
  riscv_inst32_t         inst;
  controller_if          ctrl_if();
  memory_if #(.AW(11), . DW(32)) dmem_if();

  assign imem_addr = valid ? addr : pc;

  instruction_memory
  #()
  imem_inst(
    .clk   ( clk     ),
    .addr  ( imem_addr[8:0] ),
    .rdata ( inst    ),
    .wr    ( valid   ),
    .wdata ( data    )
  );

  controlpath cp_inst(
    .inst ( inst),
    .ctrl ( ctrl_if )
  );

  datapath
  #(.AW(ADDR_WIDTH))
  dp_inst (
    .clk     ( clk     ),
    .rst_n   ( rst_n   ),
    .pc      ( pc      ),
    .inst    ( inst    ),
    .ctrl    ( ctrl_if ),
    .dmem_if ( dmem_if )
  );

  data_memory
  #()
  dmem_inst(
    .clk    ( clk ),
    .mem_if ( dmem_if  )
  );

endmodule:riscv
