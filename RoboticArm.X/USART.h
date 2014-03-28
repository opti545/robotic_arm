/* 
 * File:   USART.h
 * Author: opti545
 *
 * Created on March 28, 2014, 2:10 PM
 */

#ifndef USART_H
#define	USART_H


#ifdef	__cplusplus
extern "C" {
#endif
#include "user.h"
#include <plib/usart.h>
#define START_BYTE 0x7E
#define END_BYTE 0x9A



#ifdef	__cplusplus
extern "C" {
#endif

    unsigned char update_flag_uart=0;
    unsigned char Received_Value;
    int count = 0;
    signed char packet[7] = {0};

    void USART_Init(void);
    void RX_Interrupt(void);



#ifdef	__cplusplus
}
#endif

#endif	/* USART_H */

