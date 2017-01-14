**A DTH for the Sinope Thermostat.**  

Version 1.3 finally fully integrates with SmartThings' thermostatMode capability.  

- Still integrates with Neviweb Home / Away settings
- Uses Neviweb for "auto" mode setpoints
- **Now uses SmartThings' thermostatMode capability to control Neviweb's mode settings, so now fully controllable from CoRE and other controllers.**
- **Has configurable settings for "heat" and "emergency heat" mode setpoints (via DTH preferences)**

Previous versions:


I tried to mirror the functionality of the Sinope controls via Neviweb.  For instance, changing your Presence will adjust the setpoint to the Auto setpoint (if at home) or the Away setpoint (if Away). Similarly, manually changing the setpoint will adjust the Mode to Manual.

You will need to add a device manually within the IDE.  Once you have added the device, go into the device preferences and add (1) your Neviweb user name (typically your email), (2) your Neviweb password, (3) the Neviweb Network Name for the thermostat and (4) the Neviweb Device Name for the thermostat (all of these are set up / adjustable via www.neviweb.com ).

This DTH has the following **Control Tiles**:

1) _Presence tile_: change presence to Home / Away.  If Presence is changed to Away, the Mode Tile is no longer usable;
2) _thermostateMode tile:_ 
3) _Setpoint Slider_: adjust setpoint to desired temperature.  Changes Mode to Manual, and if Presence is Away, then also changes Mode to Home;
4) _Setpoint up / Setpoint down buttons_ (in the Main tile).  Like the Setpoint Slider, using these buttons will change Mode to Manual, and if Presence is Away, then also changes Mode to Home.  (however, due to the slowness of Neviweb, I recommend using the slider when manually changing the setpoint by more than a degree or 2).

It also has the following **Info Tiles**:
5) _Idle / Heating_:  Identifies whether the device is currently Heating or Idle;
6) _Load_: Identifies the percent of the max load that the Thermostat is sending to the Heater.

You can use CoRE to automate any of these attributes  For example, to automate the Home/Away modes in CoRE,  use the presenceHome() or the presenceAway() actions (make sure you first turn on the expert settings in CoRE). 

You can find version 1.3 of the DTH [here](https://github.com/infofiend/Sinope-Thermostat/tree/master) (github repository of infofiend / Sinope-Thermostat / master).  

Let me know if you experience any issues. 

And, if you feel like making a donation for these community efforts, it would be greatly appreciated: https://www.paypal.me/anthonypastor . Thanks!

-Tony
