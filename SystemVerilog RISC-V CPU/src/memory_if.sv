interface memory_if();
  parameter DW = 32;
  parameter AW =  9;

  logic          wr;
  logic [AW-1:0] addr;
  logic [DW-1:0] wdata;
  logic [DW-1:0] rdata;
  logic [2:0]    readdatasel;
  logic [1:0]    writedatasel;
  logic [1:0]	 writeEnable;

  modport slave(
    input  wr,
    input  addr,
    input  wdata,
    input  readdatasel,
    input  writedatasel,
    input  writeEnable,
    output rdata
    );

  modport master(
    output  wr,
    output  addr,
    output  wdata,
    output  readdatasel,
    output  writedatasel,
    output  writeEnable,
    input   rdata

  );
endinterface: memory_if
