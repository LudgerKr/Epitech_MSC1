// Création d'une liaison série sur les broches 2 et 3
/* Branchement:
  VCC -> 3.3v
  GND -> GND
  3 -> RXD
  2 -> TXD
*/
#include <SoftwareSerial.h>                                
SoftwareSerial HC06(2, 3);                                   // TX, RX

void setup()  
{ 
HC06.begin(9600);                                                 
Serial.begin(9600);                                                
}

void loop()
{  
 if (HC06.available())                  
 {
  String commandComplete = HC06.readString();
  if (strcmp(commandComplete.c_str(), "SCD") == 0) {
    HC06.write("25");
  }
 Serial.println(commandComplete);
 }
 if (Serial.available())
 {
 HC06.write(Serial.read());
 }   
}
