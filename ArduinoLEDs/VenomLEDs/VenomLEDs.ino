#include <FastLED.h>

// How many leds in your strip?
#define NUM_LEDS 46
#define NUM_SHOOTER_LEDS 20
#define NUM_CLIMBER_LEDS 20

#define DATA_PIN 7
#define SHOOTER_DATA_PIN 6
#define CLIMBER_DATA_PIN 5

//Pin 1 and pin 2
int shooterLEDMode = 30;
int foundTarget = 32;
int flashbang = 34;

// Define the array of leds

// shooterLEDs
CRGB shooterleds[NUM_SHOOTER_LEDS];

CRGB leds[NUM_LEDS];
//Climer LEDS
CRGB climberleds[NUM_CLIMBER_LEDS];

void setup()
{
  
  LEDS.addLeds<WS2812B, SHOOTER_DATA_PIN, GRB>(shooterleds, NUM_SHOOTER_LEDS);
  LEDS.setBrightness(50);
  
  LEDS.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);
  LEDS.setBrightness(50);
  
  LEDS.addLeds<WS2812B, CLIMBER_DATA_PIN, GRB>(climberleds, NUM_CLIMBER_LEDS);
  LEDS.setBrightness(50);

  pinMode(shooterLEDMode, INPUT);
  pinMode(foundTarget, INPUT);
}

void fadeall()
{
  for (int i = 0; i < NUM_LEDS; i++)
  {
    leds[i].nscale8(250);
  }
}


void fadeallshooter()
{
  for (int i = 0; i < NUM_SHOOTER_LEDS; i++)
  {
    shooterleds[i].nscale8(250);
  }
}


void fadeallclimber()
{
  for (int i = 0; i < NUM_CLIMBER_LEDS; i++)
  {
    climberleds[i].nscale8(250);
  }
}

void loop()
{

  int LEDMode = digitalRead(shooterLEDMode);
  int targetFound = digitalRead(foundTarget);



 // First slide the led in one direction
int i = 0;
int j = 0;
int k = 0;
while(true) {
leds[i] = CRGB::Red;
shooterleds[j] = CRGB::Blue;
climberleds[k] = CRGB::Blue;
//Show the LEDs
FastLED.show();
fadeall();
i++;
j++;
k++;
if (i >= NUM_LEDS) {
i= 0;
}
if (j >= NUM_SHOOTER_LEDS) {
j= 0;
}
if (k >= NUM_CLIMBER_LEDS) {
k= 0;
}
delay(10);
}
}
