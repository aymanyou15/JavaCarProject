
/* set up l298 input pin on IN1 - 2 , IN2 - 3 , IN3 - 4 , IN4 - 5 */
#define IN1   (2)
#define IN2   (3)
#define IN3   (4)
#define IN4   (5)
#define buzz  (6)
#define ENL   (9)
#define ENR   (10)

int f = 0;
int speedValue;
void setup()
{
  Serial.begin(9600);
  pinMode(13, OUTPUT);
  /* set up l298 input pin as Output pin*/
  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);
  pinMode(buzz,  OUTPUT);

  pinMode(ENL, OUTPUT);
  pinMode(ENR, OUTPUT);

  analogWrite(ENL, 0);
  analogWrite(ENR, 0);

}

void loop()
{

  f = Serial.read();

  if (f <= 20 && f >= 0)
  {
    f = f * 12.75;
    analogWrite(ENL, f);
    analogWrite(ENR, f);
    Forward_Motion();
  }
  else if (f <= 42 && f >= 22)
  {
    f = (f - 22) * 12.75;
    analogWrite(ENL, f);
    analogWrite(ENR, f);
    Backward_Motion();
  }
  else if (f <= 64 && f >= 44)
  {
    f = (f - 44) * 12.75;
    analogWrite(ENL, f);
    analogWrite(ENR, f);
    Left_Motion();
  }
  else if (f <= 86 && f >= 66)
  {
    f = (f - 66) * 12.75;
    analogWrite(ENL, f);
    analogWrite(ENR, f);
    Right_Motion();
  }
  else if (f == 100)
  {
    digitalWrite(buzz, HIGH);
  }
  else if (f == 120)
  {
    digitalWrite(buzz, LOW);
  }

  else
  {
    Stop_Motion();
    analogWrite(ENL, 0);
    analogWrite(ENR, 0);
  }

}




void Forward_Motion(void)
{
  digitalWrite(IN1, HIGH);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, HIGH);
  digitalWrite(IN4, LOW);
}
void Backward_Motion(void)
{
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, HIGH);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, HIGH);
}
void Left_Motion(void)
{
  digitalWrite(IN1, HIGH);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, HIGH);
}
void Right_Motion(void)
{
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, HIGH);
  digitalWrite(IN3, HIGH);
  digitalWrite(IN4, LOW);
}
void Stop_Motion(void)
{
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, LOW);
}
