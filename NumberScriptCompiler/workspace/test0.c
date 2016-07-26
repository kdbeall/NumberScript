#include<stdio.h>

int main(){
//# distance per day in miles
double distancePerDay = 20.0;
double distancePerMonth = distancePerDay * 30;
double milesPerGallon = 25.0;
double gallonsPerMonth = distancePerMonth / milesPerGallon;
double dollarsPerGallon = 2.50;
double dollarsPerMonth = dollarsPerGallon * gallonsPerMonth;


printf("%f\n",dollarsPerMonth);
}
