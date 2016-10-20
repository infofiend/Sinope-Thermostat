preferences {
    input("email", "text", title: "Username", description: "Your neviweb username (usually an email address)")
    input("password", "password", title: "Password", description: "Your neviweb password")
	input("gatewayname", "text", title: "Network Name:", description: "Name of your neviweb® network")
	input("devicename", "text", title: "Device Name:", description: "Name of your neviweb® thermostat")
    
//    input("devicid", "text", title: "Device Id:", description: "Device number for the thermostat (from 1 to 255)")
//    input("temperatureUnit", "enum", title: "Temperature Unit:", options: ["fahrenheit", "celsius"])
//    input("temperatureAdj", "enum", title: "Temperature adjustment:", description: "Device number for the thermostat (from -10 to +10)", 
//    	options: ["-10", "-9", "-8", "-7", "-6", "-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"])
}

metadata {
    definition (name: "Sinope Thermostat with Mode", namespace: "infofiend", author: "anthony pastor") {
        capability "Polling"
        capability "Thermostat"
        capability "Thermostat Heating Setpoint"
        capability "Thermostat Mode"
        capability "Thermostat Operating State"
        capability "Temperature Measurement"
        capability "Actuator"
        capability "Sensor"

        
        command "presenceHome"
        command "presenceAway"
        command "setPresence", ["string"]
        command "heatingSetpointUp"
        command "heatingSetpointDown"
        command "setHeatingSetpoint"	//, ["number"]
        command "getTempUnit"
        command "setFahrenheit"
        command "setCelsius"
        command "auto"
        command "off"
        command "poll"
		command "logout"
        command "login"
        command "selTempSetpoint"	//, ["number", "number"]
        command "getBackgroundColor"	//, ["number"]        
        
        
        attribute "thermMode", "string"
        attribute "thermPresence", "string"        
        attribute "thermLoad", "string"
        attribute "temperatureUnit", "string"
        attribute "realname", "string"
    }
    
    simulator {
        // TODO: define status and reply messages here
    }
    
    tiles (scale: 2) {
  		multiAttributeTile(name:"thermostatFull", type:"thermostat", width:6, height:4) {
			tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {                
				attributeState("temperature", label:'${currentValue}°') //, unit:"dF")
			}
			tileAttribute("device.temperature", key: "VALUE_CONTROL") {
				attributeState("VALUE_UP", action: "heatingSetpointUp")
				attributeState("VALUE_DOWN", action: "heatingSetpointDown")
			}
			tileAttribute("device.thermostatOperatingState", key: "OPERATING_STATE") {
				attributeState("idle", label: "${name}", backgroundColor:"#44b621")
				attributeState("heating", label: "${name}", backgroundColor:"#ffa81e")			
			}
			tileAttribute("device.thermMode", key: "THERMOSTAT_MODE") {
				attributeState("Auto", label:'Auto', nextState:"Updating", defaultState: true)
                attributeState("Away", label:'Away', nextState:"Updating")
				attributeState("Standby", label:'Standby', nextState:"Updating")
                attributeState("Manual", label:'Manual', nextState:"Updating")
                attributeState("X-Bypass", label:'Manual', nextState:"Updating")
                attributeState("Updating", label:'Updating')
			}
			tileAttribute("device.heatingSetpoint", key: "HEATING_SETPOINT") {
                attributeState("--", label:'${name}', backgroundColor: "#C6C7CC", defaultState: true) //, unit:"dF"
				attributeState("heatingSetpoint", label:'${currentValue}°') //, unit:"dF")
/**                	backgroundColors: [
		            // Celsius Color Range    	        
    	    	    [value: 0, color: "#153591"],
        	    	[value: 7, color: "#1e9cbb"],
	        	    [value: 15, color: "#90d2a7"],
    	        	[value: 23, color: "#44b621"],
	        	    [value: 29, color: "#f1d801"],
    	        	[value: 33, color: "#d04e00"],
	    	        [value: 36, color: "#bc2323"],
    	    	    // Fahrenheit Color Range
        	    	[value: 40, color: "#153591"],
	            	[value: 44, color: "#1e9cbb"],
		            [value: 59, color: "#90d2a7"],
    		        [value: 74, color: "#44b621"],
        		    [value: 84, color: "#f1d801"],
            		[value: 92, color: "#d04e00"],
		            [value: 96, color: "#bc2323"]            
					]
**/                    
                
			}
		}	   
    
        valueTile("temperature", "device.temperature", width: 2, height: 2) {
			state("temperature", label:'${currentValue}°', unit:"dF",
				backgroundColors: [
                // Celsius Color Range
                [value: 0, color: "#153591"],
                [value: 7, color: "#1e9cbb"],
                [value: 15, color: "#90d2a7"],
                [value: 23, color: "#44b621"],
                [value: 29, color: "#f1d801"],
                [value: 33, color: "#d04e00"],
                [value: 36, color: "#bc2323"],
                // Fahrenheit Color Range
                [value: 40, color: "#153591"],
                [value: 44, color: "#1e9cbb"],
                [value: 59, color: "#90d2a7"],
                [value: 74, color: "#44b621"],
                [value: 84, color: "#f1d801"],
                [value: 92, color: "#d04e00"],
                [value: 96, color: "#bc2323"]
                ]
            )
        }
        
        valueTile("heatingSetpoint", "device.heatingSetpoint", width: 3, height: 2, inactiveLabel: false, decoration: "flat") {
			state "heat", label:'SP: ${currentValue}°', backgroundColor:"#ffffff" 
		} // , unit: "dF"
              
        standardTile("thermostatOperatingState", "device.thermostatOperatingState", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
				state "idle", label:'${name}', backgroundColor:"#44b621"
				state "heating", label:'${name}', backgroundColor:"#ffa81e"
		}
        
        standardTile("thermLoad", "device.thermLoad", width: 2, height: 2) { // ,canChangeIcon: true
        	state "idle", label:'Load: 0%              '
            state "load", label:'Load:${currentValue}%              ', defaultState: true, backgroundColors: [
            // Celsius Color Range
            [value: 0, color: "#ffffff"], 
            [value: 1, color: "#153591"],
            [value: 7, color: "#1e9cbb"],
            [value: 15, color: "#90d2a7"],
            [value: 23, color: "#44b621"],
            [value: 29, color: "#f1d801"],
            [value: 33, color: "#d04e00"],
            [value: 36, color: "#bc2323"],
            // Fahrenheit Color Range
            [value: 40, color: "#153591"],
            [value: 44, color: "#1e9cbb"],
            [value: 59, color: "#90d2a7"],
            [value: 74, color: "#44b621"],
            [value: 84, color: "#f1d801"],
            [value: 92, color: "#d04e00"],
            [value: 100, color: "#bc2323"]
            ]
            
        }
        
       standardTile("thermPresence", "device.thermPresence", inactiveLabel: false, width: 3, height: 3) { //, decoration: "flat"
			state "Home", label:'Present', action:'presenceAway', backgroundColor: "#ADD8E6", icon: "st.Home.home2", nextState:"Updating"
			state "Away", label:'Away', action:'presenceHome', backgroundColor: "#44b621", icon: "st.Transportation.transportation2", nextState:"Updating"
            state "Updating", label:"Updating", backgroundColor: "#ffffff", icon: "st.secondary.secondary", defaultState: true
            
		}

        standardTile("thermMode", "device.thermMode", inactiveLabel: false, width: 3, height: 3, decoration: "flat" ) { 	//
            state "Auto", label:'', action:'thermostat.off', backgroundColor: "#ADD8E7", icon: "st.thermostat.auto", nextState:"Updating"
            state "Standby", label:'', action:'thermostat.auto', backgroundColor:"#C6C7CC", icon: "st.thermostat.heating-cooling-off", nextState:"Updating"
            state "Manual", label:'Manual Mode', action:'thermostat.auto', backgroundColor: "#90d2a7", icon: "st.Office.office12", nextState:"Updating"
            state "X-Bypass", label:'Manual Mode', action:'thermostat.auto', backgroundColor: "#90d2a7", icon: "st.Office.office12", nextState:"Updating"
            state "Away", label:' Mode N/A ', backgroundColor: "#ffffff", nextState:"Updating" // icon: "st.secondary.secondary", 
            state "Updating", label:"Updating", action:'thermostat.auto', backgroundColor: "#ffffff", icon: "st.secondary.secondary", defaultState: true        
        }
        
        
        standardTile("realdeviceName", "device.realname", height: 2, width:4, inactiveLabel: false, decoration: "flat") {
            state "realname", label:'${currentValue}     ' , defaultState: true 	//, backgroundColor: "#359115"
        }
        
        standardTile("refresh", "device.refresh", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
            state "refresh", action:"polling.poll", icon:"st.secondary.refresh", defaultState: true
        }
        
        valueTile("temperatureUnit", "device.temperatureUnit", width: 2, height: 2, canChangeIcon: false) {
            state "fahrenheit",  label: "°F"
            state "celsius", label: "°C"
        }
        
        controlTile("heatSliderControl", "device.heatingSetpoint", "slider", width: 3, height: 2, inactiveLabel: false) {
            state "setHeatingSetpoint", label:'Setpoint is ', action:"thermostat.setHeatingSetpoint", range: "(60..86)"
        }

        standardTile("heatingSetpointUp", "device.heatingSetpoint", width: 2, height: 1, inactiveLabel: false, decoration: "flat") {
            state "heatingSetpointUp", label:'  ', action:"heatingSetpointUp", icon:"st.thermostat.thermostat-up", backgroundColor:"#bc2323"
        }
        
        standardTile("heatingSetpointDown", "device.heatingSetpoint", width: 2, height: 1, inactiveLabel: false, decoration: "flat") {
            state "heatingSetpointDown", label:'  ', action:"heatingSetpointDown", icon:"st.thermostat.thermostat-down", backgroundColor:"#bc2323"
        }

        main(["thermostatFull"])
        details(["thermostatFull","thermPresence","thermMode", "heatSliderControl", "heatingSetpoint", "thermostatOperatingState","thermLoad","temperatureUnit","refresh"]) 

        
    }
            //  details(["thermPresence","thermMode", "thermostatOperatingState", "heatSliderControl", "heatingSetpoint", "temperature","temperatureUnit", "refresh"]) // "heatingSetpointDown", "heatingSetpointUp",
}


def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	
	initialize()
}

def initialize() {
	if (!state.gatewayId || !state.deviceId || !state.dataAuth) {
    	login()
	}         
	
    poll()

    schedule("0 0 7 1/1 * ? *", poll) 
}

// handle commands
def parse(String description) {
	log.debug "parse: description = ${description}"
    
}

//		PRESENCE FUNCTIONS
def presenceAway() {
	log.trace "presenceAway: "
	unschedule(logout)    
	setPresence('Away')
}

def presenceHome() {
	log.trace "presenceHome: " 
    unschedule(logout)
    setPresence('Home')
}

private setPresence(status) {
	log.trace "setPresence(${status}: "    
    sendEvent(name: "thermMode", value: "Updating", isStateChange:true)
    
	if(!isLoggedIn()) {
		log.debug "Need to login"
        login()
	}
	if(data.error==true){
		logout()
	} else { 
    	def thermostatID= state.deviceId
		switch (status) {
        
			case "Home":
				log.trace "setPresence: Home"        
            	log.debug "Switching Presence to Home / data.status.mode = 3"           
	            def putParams = [
					uri: "https://neviweb.com/api/device/${thermostatID}/mode",
        			headers: ['Session-Id' : data.auth.session],
            		body: "mode=3"
	    		]
    	        httpPut(putParams)	{resp ->
					log.debug resp.data
				}
            
	            break;
            
			case "Away":
				log.trace "setPresence: Away "        
	            state.lastPresence = device.currentValue("thermPresence") 
    	        log.debug "Switching Presence to Away / data.status.mode = 5"
        	    def putParams = [
					uri: "https://neviweb.com/api/device/${thermostatID}/mode",
    	    		headers: ['Session-Id' : data.auth.session],
	        	    body: "mode=5"
    			]
        	    
				httpPut(putParams)	{resp ->
					log.debug resp.data
				}	
            
        	    break;
		}
 
	schedule (now() + 2500, poll )
	}
}


//		MANUAL SETPOINT FUNCTIONS
def setHeatingSetpoint(temp) {
	log.trace "heatingSetpoint( ${temp} ):"
//	unschedule(logout)

	if(!isLoggedIn()) {
		log.debug "Need to login"
		login()
		
	}
	if(data.error == true){
		logout()
	} else {
    	def thermostatID= state.deviceId

	    if ( device.latestState('thermMode') == 'Away' ) {
		    def putParams = [
				uri: "https://neviweb.com/api/device/${thermostatID}/mode",
        		headers: ['Session-Id' : data.auth.session],
	           	body: "mode=3"
    		]
        	httpPut(putParams)	{resp ->
				log.debug resp.data
			}
    	    sendEvent(name: 'thermPresence', value: "Home", display: false, isStateChange: true )
	    	sendEvent(name: 'thermMode', value: "Manual", display: false, isStateChange: true) 
		}
    
		log.debug "setHeatingSetpoint: temperatureUnit = ${temperatureUnit}"
    	
//	    def thermostatID=data.devices.id [settings.devicid.toInteger()-1]
    	if ( temperatureUnit == "celsius" ) {
      		temp=temp      
		} else {
			temp=fToC(Math.round(temp))
		}
	
    	if (temp) {
			if (temp < 1) {
				temp = 1
			}
		
        	if (temp > 36) {
				temp = 36
			}              
    	} 

		def params = [
			uri: "https://neviweb.com/api/device/${thermostatID}/setpoint",
        	headers: ['Session-Id' : data.auth.session],
	        body: "temperature=${temp}"
		]
                
                 
		httpPut(params){resp ->
    		log.debug resp.data
		}	
    
		schedule (now() +  2500, poll )
	}
}


def heatingSetpointUp() {
    
    def newSetpoint 
    
    if (device.currentValue("heatingSetpoint") != "--" && device.currentValue("heatingSetpoint") != "Off: No Setpoint" ) {
		newSetpoint = device.currentValue("heatingSetpoint") as Number
	}
    
    log.trace "heatingSetpointUp: Last setpoint was (${newSetpoint}) degrees ${temperatureUnit}."
    
	if (temperatureUnit == "celsius" ) {
		newSetpoint = newSetpoint + 0.5 
	} else {
		newSetpoint = Math.round(newSetpoint + 1)
	} 
    
    log.debug "Setting heat set point up to: ${newSetpoint} degrees ${temperatureUnit}."
    setHeatingSetpoint(newSetpoint)

}

def heatingSetpointDown() {	
    
	def newSetpoint = device.currentValue("heatingSetpoint")
    log.trace "heatingSetpointDown: Last setpoint was (${newSetpoint}) degrees ${temperatureUnit}."
    
	if (temperatureUnit == "celsius" ) {
		newSetpoint = newSetpoint - 0.5 
	} else {
		newSetpoint = Math.round(newSetpoint - 1)
	} 
    
    log.debug "Setting heat set point down to: ${newSetpoint} degrees ${temperatureUnit}."
    setHeatingSetpoint(newSetpoint)

}



//		TEMPERATURE UNIT FUNCTIONS
def getTempUnit() {

	state.tempUnit = device.currentValue("temperatureUnit")

}

def setFahrenheit() {
    def temperatureUnit = "fahrenheit"
    log.debug "Setting temperatureUnit to: ${temperatureUnit}"
    sendEvent(name: "temperatureUnit", value: temperatureUnit, isStateChange: true, display: false)
    
}

def setCelsius() {
    def temperatureUnit = "celsius"
    log.debug "Setting temperatureUnit to: ${temperatureUnit}"
    sendEvent(name: "temperatureUnit", value: temperatureUnit, isStateChange: true, display: false)
    
}


//		AUTO & OFF FUNCTIONS
def off() {
	log.trace "off():"
//	unschedule(logout)
    
    if(!isLoggedIn()) {
        log.debug "Need to login"
        login()
    }
	if(data.error==true){
		logout()
	} else {
    
	    state.lastSetpoint = device.currentValue('heatingSetpoint')
    	def thermostatID= state.deviceId
	               
	    def putParams = [
    	    uri: "https://neviweb.com/api/device/${thermostatID}/mode",
        	headers: ['Session-Id' : data.auth.session],
	        body: ['mode': 0]
    	]
	    httpPut(putParams) { resp ->
			log.debug resp.data
		}

		schedule (now() +  2500, poll )
	}
}

def auto() {
	log.trace "auto():"
//	unschedule(logout)
    
    if(!isLoggedIn()) {
        log.debug "Need to login"
        login()
    }	
	
    if (data.error==true) {
		logout()
	} else {   
    
    	def thermostatID= state.deviceId	               
    	def putParams = [
	        uri: "https://neviweb.com/api/device/${thermostatID}/mode",
    	    headers: ['Session-Id' : data.auth.session],
        	body: ['mode': 3]
	    ]
    	httpPut(putParams) { resp ->
			log.debug resp.data
		}

		schedule (now() +  2500, poll )
	}
}

// 	POLL
def poll() {
	log.trace "poll():"
//	unschedule(logout)
    
    if(!isLoggedIn() && !state.dataAuth) {
        log.debug "Need to login"
        login()
    }

    if (data.error==true) {
		logout()
	} else {   

	    def myPresence = ""
    	def myMode = ""
        
    	def thermostatID= state.deviceId 
        def dataAuthSession = state.dataAuth.session
        log.debug "dataAuthSession == ${dataAuthSession}" 
        
		def getParams = [
   	    	uri: "https://neviweb.com/api/device/${thermostatID}/data",
	       	requestContentType: "application/x-www-form-urlencoded; charset=UTF-8",
    		headers: ['Session-Id' : data.auth.session]
   		]
	        
	   	httpGet(getParams) { resp ->
			data.status = resp.data
		}
        
		log.debug "data == ${data}"
	 	log.debug "data.gateway_list== ${data.gateway_list}"
		log.debug "data.gateway_list == ${data.gateway_list.id}"        
		log.debug "data.devices_list == ${data.devices_list}"
        log.debug "data.deviceId == ${data.deviceId}" 
        
    	log.debug "data.status == ${data.status}"
        log.debug "*****************data.status.mode == ${data.status.mode}"
    
	    switch (data.status.mode) {		
    		case 0: 			
				myPresence = "Home"
        		myMode = "Standby"            
				break;
    
		    case 2: 			
				myPresence = "Home"
	        	myMode = "Manual"
				break;
    
		    case 3: 			
				myPresence = "Home"
	        	myMode = "Auto"
				break;
    	
	    	case 5: 			
				myPresence = "Away"
		        myMode = "Away"
				break;

	    	case 131: 			
				myPresence = "Home"
		        myMode = "X-Bypass"
				break;
	
		}            

	    def mySetpoint
    	def myTemp
	    def spLabel
    	def myColor
	    def correctSPT = [:]     
    	if (data.status.mode != 0) {
		    correctSPT = selTempSetpoint(data.status.setpoint, data.status.temperature)
    	    mySetpoint = correctSPT.sp 
        	spLabel = "${correctSPT.sp}°"
	    	myColor = getBackgroundColor(mySetpoint)
    	    myTemp = correctSPT.t        
		} else {        
			correctSPT = selTempSetpoint(null, data.status.temperature)
	    	mySetpoint = null
	        spLabel = "Off: No Setpoint"
    		myColor = "#C6C7CC"
			myTemp = correctSPT.t
	    }
	
	    def myLoad = data.status.heatLevel as Number
    	log.debug " myLoad == ${myLoad}"
	    def myState = "idle"
    	if (myLoad > 0) { myState = "heating" }
    
	    sendEvent(name: 'thermPresence', value: myPresence, display: false) //isStateChange: true,
    	sendEvent(name: 'thermMode', value: myMode, display: false)	    //isStateChange: true, 
	    sendEvent(name: 'heatingSetpoint', value: mySetpoint, label: spLabel, backgroundColor: "${myColor}", display: false) 	//isStateChange: true, 
		sendEvent(name: 'temperature', value: myTemp, display: false)	//isStateChange: true, 
		sendEvent(name: 'thermLoad', value: myLoad, display: false)		//isStateChange: true, 
		sendEvent(name: 'thermostatOperatingState', value: myState, isStateChange: true, display: false)	
    
		log.debug "My Presence is .....${myPresence}"        
    	log.debug "My Mode is .....${myMode}"	
		log.debug "My setpoint is .....${mySetpoint}"
    	log.debug "My temperature is .....${myTemp}"
	    log.debug "My power load is .....${myLoad}"
    	log.debug "My operating state is .....${myState}"    	            
	    
//	    def mydevice=data.devices.name[settings.devicid.toInteger() - 1]        
//		sendEvent(name: 'realname', value: "${mydevice}", isStateChange: true)

//		schedule(now() + 300000, logout)
	}	
}


// UTILIY FUNCTIONS

def cToF(temp) {
	return ((( 9 * temp ) / 5 ) + 32)
	log.info "celsius -> fahrenheit"
}

def fToC(temp) {
	return ((( temp - 32 ) * 5 ) / 9)
	log.info "fahrenheit -> celsius"
}

def selTempSetpoint(setpoint, temp) {	
	log.trace "selTempSetpoint( ${setpoint}, ${temp} ): "
	log.debug "temperatureUnit = ${temperatureUnit}"
	def mySP = setpoint as Double
    def myT = temp as Double    
	switch (temperatureUnit) {
        case "celsius":
			if (setpoint) {		
            	mySP = mySP
            } 
	        myT = myT 
            break;
       	default:
			if (setpoint) {		
            	mySP = Math.round(cToF(mySP)) as Double
			}                
    	    myT = Math.round(cToF(myT))
        	break;	    
	}     
    log.debug "In ${temperatureUnit}, setpoint is ${mySP} and temperature is ${myT}."
     
    return [sp: mySP, t: myT]

}

def FormatTemp(temp){

	def temperatureUnit = device.latestValue('temperatureUnit')
	
    if (temp!=null){
		float i=Float.valueOf(temp)
		switch (temperatureUnit) {
	        case "celsius":
				return (Math.round(i*2)/2).toDouble().round(2)
				log.warn((Math.round(i*2)/2).toDouble().round(2))
	        break;

	        case "fahrenheit":
	        	return (Math.ceil(cToF(i))).toDouble().round(2)
	        	log.warn(Math.ceil(cToF(i)).toDouble().round(2))
	        break;
	    }
    } else {
    	return null
    }
}


private getBackgroundColor(tempValue){
	log.trace "getBackgroundColor(${tempValue})"	

    def backgroundColor

    if (tempValue < 7 ){
        backgroundColor = "#153591"
    }
    else if (tempValue>=7 && tempValue <15 ){
        backgroundColor = "#1e9cbb"
    }
    else if (tempValue>=15 && tempValue <23 ){
        backgroundColor = "#90d2a7"
    }
    else if (tempValue>=23 && tempValue <29 ){
        backgroundColor = "44b621"
    }
    else if (tempValue>=29 && tempValue <33 ){
        backgroundColor = "#f1d801"
    }
    else if (tempValue>=33 && tempValue <36 ){
        backgroundColor = "#d04e00"
    }
    else if (tempValue>=36 && tempValue <40 ){
        backgroundColor = "#bc2323"
    }
    else if (tempValue>=40 && tempValue <44 ){
        backgroundColor = "#153591"
    }
    else if (tempValue>=44 && tempValue <59 ){
        backgroundColor = "#1e9cbb"
    }
    else if (tempValue>=59 && tempValue <74 ){
        backgroundColor = "#90d2a7"
    }
    else if (tempValue>=74 && tempValue <84 ){
        backgroundColor = "44b621"
    }
    else if (tempValue>=84 && tempValue <92 ){
        backgroundColor = "#f1d801"
    }
    else if (tempValue>=92 && tempValue <96 ){
        backgroundColor = "#d04e00"
    }
    else if (tempValue>=96 ){
        backgroundColor = "#bc2323"
    }
    
    return backgroundColor
}       


//		LOGIN FUNCTIONS

def login() {
	log.trace "login():"	
    
    
		data.server="https://neviweb.com/"
    	def params = [
	        uri: "${data.server}",
    	    path: 'api/login',
        	requestContentType: "application/x-www-form-urlencoded; charset=UTF-8",
	        body: ["email": settings.email, "password": settings.password, "stayConnected": "0"]
    	]
		log.debug "login params = ${params}"
    	httpPost(params) { resp ->
	        data.auth = resp.data
    	    if (data.auth.error){
        		log.warn(data.auth.error)
        		sendEvent(name: 'temperature', value: "ERROR LOGIN", state: temperatureType)
	        	log.error("Authentification failed or request error")
    	    	data.error=true
        		logout()
	    	} else {
    			log.info("login and password :: OK")
        		data.error=false
        		gatewayId()
                state.dataAuth = data.auth
	    	} 
    	}

}


def logout() {
	log.trace "logout():"
    
      	def params = [
			uri: "${data.server}",
	        path: "api/logout",
	       	requestContentType: "application/x-www-form-urlencoded; charset=UTF-8",
	        headers: ['Session-Id' : data.auth.session]
    	]
        httpGet(params) {resp ->
			data.auth = resp.data
        }
        log.info("logout :: OK")  
}

def isLoggedIn() {
	log.trace "isLoggedIn():"
    
	log.info ("Is it login?")
	if (data?.auth?.session!=null){
		try{
			def params = [
				uri: "${data.server}",
			    path: "api/gateway",
			   	requestContentType: "application/json, text/javascript, */*; q=0.01",
			    headers: ['Session-Id' : data.auth.session]
			]
			httpGet(params) {resp ->
			    if(resp.data.sessionExpired==true){
			    	log.info "No session Expired"
			    	data.auth=""
			    }
			}
			if(!data.auth) {
				return false
				log.error("not pass log")
			} else {
				if (data?.deviceId!=null){
					return true
				}else{
					return false
					log.error("No device or gateway with this name.")
				}
			}
		}catch (e){
			log.error(e)
			return false
		}
	}else{
		return false
	}
}


     

def gatewayId(){
	log.trace "gatewayId:"
  	log.trace "Already have state.gatewayId"    
    
    if (state.gatewayId && state.gatewayId !=null) {
    	deviceId()
    } else {    
		def params = [
			uri: "${data.server}",
        	path: "api/gateway",
	       	requestContentType: "application/json, text/javascript, */*; q=0.01",
    	    headers: ['Session-Id' : data.auth.session]
	    ]
    	httpGet(params) { response ->
        	data.gateway_list = response.data
	    }
    	log.debug "data.gateway_list = ${data.gateway_list}"
    
	    def gatewayName = settings.gatewayname
		gatewayName = gatewayName.toLowerCase().replaceAll("\\s", "")
    
	    log.debug "Gateway Name from Settings = ${gatewayName}"    
    
		for(var in data.gateway_list){

	    	def name_gateway = var.name
    		name_gateway = name_gateway.toLowerCase().replaceAll("\\s", "")
			log.debug "name_gateway from Neviwab = ${name_gateway}"
	    	if(name_gateway == gatewayName){
    	    	state.gatewayId = data.gatewayId
        	    log.debug "state.gatewayId == ${var.id}"
    			data.gatewayId = var.id
	    		log.info("gateway ID is :: ${var.id}")
    			data.error = false
    			deviceId()
	    	}
    	}
        
	    if (data?.gatewayId== null){
	    	sendEvent(name: 'temperature', value: "ERROR GATEWAY", state: temperatureType)
    		log.error("No gateway with this name or request error")
    		data.error = true
	    	logout()
    	}
	}
}

def deviceId(){
	log.trace "deviceId:"
  	log.trace "Already have state.deviceId"    
    if (state.deviceId && state.deviceId !=null) {
    	DeviceData()
    } else {    
    
		def params = [
			uri: "${data.server}",
        	path: "api/device",
	        query: ['gatewayId' : data.gatewayId],
    	   	requestContentType: "application/json, text/javascript, */*; q=0.01",
        	headers: ['Session-Id' : data.auth.session]
	   	]
    	httpGet(params) {resp ->
			data.devices_list = resp.data
	    }
    	log.debug "data.devices_list ${data.devices_list}" 
	    def deviceName=settings.devicename
		deviceName=deviceName.toLowerCase().replaceAll("\\s", "")
	    log.debug "Device Name from Settings = ${deviceName}"
    	for(var in data.devices_list){
	    	def name_device=var.name
    		name_device=name_device.toLowerCase().replaceAll("\\s", "")

			log.debug "name_device from Neviwab = ${name_device}"

	    	if(name_device==deviceName){
    	    	state.deviceId = var.id
        	    log.debug "state.deviceId == ${var.id}"
    			data.deviceId=var.id
	    		log.info("device ID is :: ${data.deviceId}")
    			DeviceData()
    			data.error=false
	    	}	
    	}
	    if (data?.deviceId==null){
    		sendEvent(name: 'temperature', value: "ERROR DEVICE", state: temperatureType)
    		log.error("No device with this name or request error")
	    	data.error=true
    		logout()
	    }	
	}
}

def DeviceData(){
	log.trace "DeviceData:"

	def temperature
    def heatingSetpoint
    def range
	def temperatureUnit
	
    data.server="https://neviweb.com/"
	def thermostatID= state.deviceId       

   	def params = [
		uri: "${data.server}api/device/${thermostatID}/data?force=1",
		requestContentType: "application/x-www-form-urlencoded; charset=UTF-8",
        headers: ['Session-Id' : data.auth.session]
    ]
    log.info "params = ${params}"
    
    httpGet(params) {resp ->
		data.status = resp.data
    }
	log.info("Data.status is :: ${data.status}")

    if(data?.auth?.user?.format?.temperature == "c"){
    	temperatureUnit = "celsius"
    }else{
    	temperatureUnit = "fahrenheit"
    }
    
    sendEvent(name: "temperatureUnit",  value: temperatureUnit)
    
    switch (temperatureUnit) {

        case "celsius":
        	log.info("celsius temperature")
        	temperature = FormatTemp(data.status.temperature)
        	heatingSetpoint = FormatTemp(data.status.setpoint)

        break;

        case "fahrenheit":
        	log.info("fahrenheit temperature")
        	temperature = FormatTemp(data.status.temperature)
        	heatingSetpoint = FormatTemp(data.status.setpoint)
        break;
    }
    
	sendEvent(name: 'temperature', value: temperature, unit: temperatureUnit)	
	sendEvent(name: 'heatingSetpoint', value: heatingSetpoint, unit: temperatureUnit)
    sendEvent(name: 'thermostatOperatingState', value: "${data.status.heatLevel}")
}

