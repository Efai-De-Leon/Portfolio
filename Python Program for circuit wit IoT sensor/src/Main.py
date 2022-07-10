#Author: Efai De Leon
# Imports 
import cimis_extract as CIMIS
from lib.adafruit import DHT11 as DHT
import time
import threading
from lib.adafruit import I2CLCD1602 as lcd
from lib.adafruit.PCF8574 import PCF8574_GPIO
from lib.adafruit.Adafruit_LCD1602 import Adafruit_CharLCD 

# Pin Definitions 
infrared_sensor = 15
relay = 13
DHT_11 = 11
blue_LED = 16
button = 12
# Global Variables
global TH                   # TH holds the temperature and humidity values in string format
TH = "\n"

def lcd_thread():           # LCD thread
    start = time.time()
    threshold = 30          # time in seconds the thread is alive
    mcp.output(3,1)         # turn on LCD backlight
    lcd.begin (16, 2)       # set number of LCD lines and columns

    while True:
        lcd.setCursor(0,0)      # set cursor position
        global TH 
        lcd.clear()             # clearing the LCD before sending a messsage
        lcd.message(TH + '\n')
        time.sleep(1)           # rate at which we update the LCD
        if time.time() - start > threshold:
            break
    print("Exiting lcd thread")

def th_thread():            # temperature and humidity thread
    start = time.time()
    threshold = 30          # time in seconds the thread is alive
    while True:
        global TH
        TH = DHT.loop()     # DHT.loop() return the temperature and humidity values in string format
        #DHT.loop()
        time.sleep(5)
        if time.time() - start > threshold:
            break
    print("Exiting th thread")


try:
   PCF8574_address = 0x27
   mcp = PCF8574_GPIO(PCF8574_address)  # I2C address of the PCF8574 chip.
   lcd = Adafruit_CharLCD(pin_rs=0, pin_e=2, pins_db=[4,5,6,7], GPIO=mcp)
except:
    print("Unexpected Error")
def main():
    CIMIS.extractData()

    t1 = threading.Thread(target = th_thread)
    t2 = threading.Thread(target = lcd_thread)
    t1.daemon = True
    t2.daemon = True
    t1.start()
    t2.start()

def setup():
    GPIO.setmode(GPIO.BOARD)

    # need to insert setmodes? -justin

if __name__ == "__main__":
    main()
