#include "USART.h"
#include <plib/usart.h>
#define _XTAL_FREQ  8000000

void USART_Init(void){

    unsigned char UART1Config = USART_TX_INT_OFF &
           USART_RX_INT_ON &
           USART_ASYNCH_MODE &
           USART_EIGHT_BIT &
           USART_BRGH_HIGH &
           USART_ADDEN_OFF ;
    unsigned char spbrg = 51;
    OpenUSART(UART1Config,spbrg);
    PIR1bits.RCIF = 0; //reset RX pin flag
    IPR1bits.RCIP = 1; //high priority
    PIE1bits.RCIE = 1; //Enable RX interrupt
    INTCONbits.PEIE = 1; //Enable pheripheral interrupt (serial port is a pheripheral)
    RCSTAbits.CREN = 1;
    TRISCbits.RC7 = 1; //RX Pin as input
    INTCONbits.GIEL=1; //Enable General Interrupts
    INTCONbits.GIEH=1; //Enable General Interrupts
}


//Interrupt for RX Data
void RX_Interrupt(void){

         Received_Value=RCREG;

         if(count == 0 && Received_Value == START_BYTE){
            packet[count]=(int)Received_Value;
            count++;
            PIR1bits.RCIF=0;
            return;
         }
         if(count>0 && count <=5){
            packet[count]=(int)Received_Value;
            count++;
            PIR1bits.RCIF=0;
            return;
         }
         if(count == 6 && Received_Value == END_BYTE){
             packet[count]=(int)Received_Value;
             count=0;
             PIR1bits.RCIF=0;
             update_flag_uart=1;
             return;
         }
         PIR1bits.RCIF=0;
         return;
}
