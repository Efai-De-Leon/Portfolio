
module data_memory
  #(
    parameter AW = 11,
    parameter DW = 32
  )
  (
    input  logic          clk,
    memory_if.slave       mem_if
  );
 
  localparam MEM_CUTS = 4;

  memory_if #(.AW( AW- $clog2(MEM_CUTS)), .DW(8)) sub_mem_if[MEM_CUTS]();
 
  logic [7:0] dout[4];
  logic [7:0] din [4];

  assign din[0] = mem_if.wdata[ 7: 0];
  assign din[1] = mem_if.wdata[15: 8];
  assign din[2] = mem_if.wdata[23:16];
  assign din[3] = mem_if.wdata[31:24];

  logic [1:0] byte_m1_q;
  logic [1:0] offset_q;
  logic [MEM_CUTS-1:0] wr_enable;

  always @(posedge clk) byte_m1_q <= mem_if.writeEnable;
  always @(posedge clk) offset_q  <= mem_if.addr[1:0];
  
  always_comb begin
      case (mem_if.writeEnable)
      3'b001: // Half-Word
      begin
        case (mem_if.addr[0])
        1'b0:begin
              sub_mem_if[0].wdata = din[0];
              sub_mem_if[1].wdata = din[1];
              wr_enable = 4'b0011;
             end
        1'b1:begin
              sub_mem_if[2].wdata = din[0];
              sub_mem_if[3].wdata = din[1];      
              wr_enable = 4'b1100;
             end
        default:
          wr_enable = 4'b0000;
        endcase
      end
      3'b000: // One Byte
      begin
        case (mem_if.addr[1:0])
        2'b00:
          begin
            sub_mem_if[0].wdata = din[0];
            wr_enable = 4'b0001;
          end
        2'b01:
          begin
            sub_mem_if[1].wdata = din[0];
            wr_enable = 4'b0010;
          end
        2'b10:
          begin
            sub_mem_if[2].wdata = din[0];
            wr_enable = 4'b0100;
          end
        2'b11:
          begin
            sub_mem_if[3].wdata = din[0];
            wr_enable = 4'b1000;
          end
        default:
          wr_enable = 4'b0000;
        endcase
      end
      default:
         begin
          sub_mem_if[0].wdata = din[0];
          sub_mem_if[1].wdata = din[1];
          sub_mem_if[2].wdata = din[2];
          sub_mem_if[3].wdata = din[3];
          
          wr_enable = 4'b1111;
         end
      endcase
    end

  always_comb begin
      case (byte_m1_q)
      3'b001: // Half-Word
      begin
        case (offset_q[0])
        1'b0:begin
              dout[0] = sub_mem_if[2].rdata;
              dout[1] = sub_mem_if[3].rdata;
             end
        1'b1:begin
              dout[0] = sub_mem_if[0].rdata;
              dout[1] = sub_mem_if[1].rdata;        
             end
        default:
          dout[0] = 0;
        endcase
      end
      3'b000: // One Byte
      begin
        case (offset_q)
        2'b00:
            dout[0] = sub_mem_if[0].rdata;
        2'b01:
            dout[0] = sub_mem_if[1].rdata;
        2'b10:
            dout[0] = sub_mem_if[2].rdata;
        2'b11:
            dout[0] = sub_mem_if[3].rdata;
        default:
          dout[0] = 0;
        endcase
      end
      default:
         begin
          dout[0] = sub_mem_if[0].rdata ;
          dout[1] = sub_mem_if[1].rdata ;
          dout[2] = sub_mem_if[2].rdata ;
          dout[3] = sub_mem_if[3].rdata ;
         end
      endcase
    end

  assign mem_if.rdata = {dout[3], dout[2], dout[1], dout[0]};
 
  genvar i;
  generate 
  for(i=0; i<MEM_CUTS; i=i+1) begin: dmem
    
    assign sub_mem_if[i].wr    = mem_if.wr & wr_enable[i];
    assign sub_mem_if[i].addr  = mem_if.addr[AW-1:2];
  
    sub_data_memory 
      #(.AW( AW- $clog2(MEM_CUTS)), .DW(8)) 
    dm (
      .clk( clk ),
      .mem_if ( sub_mem_if[i] )
    );
  end
  endgenerate 

endmodule:data_memory
