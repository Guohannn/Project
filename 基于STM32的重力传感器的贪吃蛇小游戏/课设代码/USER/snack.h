
#ifndef __SNACK__H
#define __SNACK__H


#include "delay.h"
#include "key.h"
#include "sys.h"
#include "lcd.h"
#include "usart.h"

#define LEFT 1
#define RIGHT 2
#define UP 3
#define DOWN 4
#define PAUSE 5
#define SPEED 6
#define SLOW 7
#define START 8
#define STOP 9

extern u8 clear_flag; 
extern u8 DIR;
extern u16 GAME_time;
extern u8 GAME_state;//游戏进程
extern u8 Long_key;
extern u8 SNACK_L;	//蛇长
extern u8 pause_flag;
extern u16 speed;

//short pitch,roll,yaw;
//short npitch,nroll,nyaw;

void SNACK_init(void);
void SNACK(void);
void show_map(void);
void Creat_food(void);
void Game_start(void);
void Game_over(void);
void init(void);
#endif

