#include <FastLED.h>

// How many leds in your strip?
//Remember to put NUM_LEDS back to 46
#define NUM_LEDS 20
#define NUM_SHOOTER_LEDS 20

#define DATA_PIN 7
#define SHOOTER_DATA_PIN 6

//Pin 1 and pin 2
int shooterLEDMode = 30;
int foundTarget = 31;

// Define the array of leds
CRGB leds[NUM_LEDS];
// shooterLEDs
CRGB shooterleds[NUM_SHOOTER_LEDS];

void setup()
{
  LEDS.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);
  LEDS.setBrightness(50);

  LEDS.addLeds<WS2812B, SHOOTER_DATA_PIN, GRB>(shooterleds, NUM_SHOOTER_LEDS);
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

void loop()
{

  int LEDMode = digitalRead(shooterLEDMode);
  int targetFound = digitalRead(foundTarget);

  if (LEDMode == 0)
  {
    //Write some code that makes larson scanner faster

    for (int i = 0; i < NUM_SHOOTER_LEDS; i++)
    {
      shooterleds[i] = CRGB::Blue;
      FastLED.show();
      delay(15);
      shooterleds[i - 6] = CRGB::Red;
      FastLED.show();
    }
    for (int i = (NUM_SHOOTER_LEDS)-1; i >= 0; i--)
    {
      shooterleds[i] = CRGB::Red;
      FastLED.show();
      delay(15);
      shooterleds[i - 6] = CRGB::Blue;
      FastLED.show();
    }
  }
  else
  {
    if (targetFound == 1)
    {
      //Give indication for that we see target

      for (int i = 0; i < NUM_SHOOTER_LEDS; i++)
      {
        shooterleds[i] = CRGB::Blue;
        FastLED.show();
        delay(5);
        shooterleds[i - 6] = CRGB::Red;
        FastLED.show();
      }
      for (int i = (NUM_SHOOTER_LEDS)-1; i >= 0; i--)
      {
        shooterleds[i] = CRGB::Red;
        FastLED.show();
        delay(5);
        shooterleds[i - 6] = CRGB::Blue;
        FastLED.show();
      }
    }
    else
    {
      //write slower larson
      // Set the i'th led to red

      for (int i = 0; i < NUM_SHOOTER_LEDS; i++)
      {
        shooterleds[i] = CRGB::Blue;
        FastLED.show();
        delay(15);
        shooterleds[i - 6] = CRGB::Red;
        FastLED.show();
      }
      for (int i = (NUM_SHOOTER_LEDS)-1; i >= 0; i--)
      {
        shooterleds[i] = CRGB::Red;
        FastLED.show();
        delay(15);
        shooterleds[i - 6] = CRGB::Blue;
        FastLED.show();
      }
    }

    // First slide the led in one direction
    for (int i = 0; i < NUM_LEDS; i++)
    {
      // Set the i'th led to red
      leds[i] = CRGB::Blue;
      // Show the leds
      FastLED.show();
      fadeall();
      delay(100);
    }
    for (int i = 0; i < NUM_LEDS; i++)
    {
      // Set the i'th led to red
      leds[i] = CRGB::Yellow;
      // Show the leds
      FastLED.show();
      fadeall();
      delay(100);
    }
    for (int i = 0; i < NUM_LEDS; i++)
    {
      // Set the i'th led to red
      leds[i] = CRGB::Purple;
      // Show the leds
      FastLED.show();
    }

    //fadeall();
    delay(100);
  }
}
