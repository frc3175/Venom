#include <FastLED.h>

// How many leds in your strip?
#define NUM_LEDS 25 // actual 46
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
CRGB leds[NUM_LEDS];
CRGB leds1[NUM_LEDS];
// shooterLEDs
CRGB shooterleds[NUM_SHOOTER_LEDS];
//Climer LEDS
CRGB climberleds[NUM_CLIMBER_LEDS];

void setup()
{
  LEDS.addLeds<WS2812B, DATA_PIN, GRB>(leds, 0, NUM_LEDS);
  LEDS.setBrightness(50);
  
  LEDS.addLeds<WS2812B, DATA_PIN, GRB>(leds1, 23, NUM_LEDS);
  LEDS.setBrightness(50);

  LEDS.addLeds<WS2812B, SHOOTER_DATA_PIN, GRB>(shooterleds, NUM_SHOOTER_LEDS);
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

void fadeall1()
{
  for (int i = 0; i < NUM_LEDS; i++)
  {
    leds1[i].nscale8(250);
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


  for(int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Red;
    leds1[i] = CRGB::Red;
    shooterleds[i] = CRGB::Blue;
    climberleds[i] = CRGB::Blue;
    FastLED.show();
    fadeall();
    fadeall1();
    fadeallshooter();
    fadeallclimber();
    delay(10);
  }

    for(int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Blue;
    leds1[i] = CRGB::Blue;
    shooterleds[i] = CRGB::Red;
    climberleds[i] = CRGB::Red;
    FastLED.show();
    fadeall();
    fadeall1();
    fadeallshooter();
    fadeallclimber();
    delay(10);
  }

    for(int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Purple;
    leds1[i] = CRGB::Purple;
    shooterleds[i] = CRGB::Purple;
    climberleds[i] = CRGB::Green;
    FastLED.show();
    fadeall();
    fadeall1();
    fadeallshooter();
    fadeallclimber();
    delay(10);
  }


}
