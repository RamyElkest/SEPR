{
    "author": "LLAMAS",
    "name": "UFY (Unidentified Flying Yak)",
    "created": "January 2012",
    "description": "It is 2052, you have been selected to investigate a strange ship that has appeared in Earth's atmosphere. Explore the entire ship, searching every room completing objectives as you go. Complete all of the objectives and you will return to Earth a hero. Just watch out for the alien crewmembers aboard as they will attack! Hint: Make sure you USE a weapon so that your player can wield it in fights, otherwise they won't stand much of a chance!",
   "objectives": [{
        "command": {
            "commandType": "ATTACK",
            "entity-id": "fuelcell1"
        },
        "onCompleteMessage": "The first fuel cell has been deactivated, the shield has lost strength.",
        "description": "Deactivate the first shield power cell."
    },
    {
        "command": {
            "commandType": "ATTACK",
            "entity-id": "fuelcell2"
        },
        "onCompleteMessage": "The second fuel cell has been deactivated, the shield has lost strength.",
        "description": "Deactivate the second shield power cell."
    },
    {
        "command": {
            "commandType": "ATTACK",
            "entity-id": "fuelcell3"
        },
        "onCompleteMessage": "The third power cell has been deactivated, the shield has lost strength.",
        "description": "Deactivate the third shield power cell."
    },
    {
        "command": {
            "commandType": "ATTACK",
            "entity-id": "fuelcell4"
        },
        "onCompleteMessage": "The fourth fuel cell has been deactivated, the shield has lost strength.",
        "description": "Deactivate the fourth shield power cell."
    },
    {
        "command": {
            "commandType": "USE",
            "entity-id": "llamafood"
        },
        "onCompleteMessage": "You've fed the baby llama!",
        "description": "Feed the llama!"
    },
    {
        "command": {
            "commandType": "LOOK",
            "entity-id": "worldmap"
        },
        "onCompleteMessage": "So this is what they're planning - they want to destroy the world!!",
        "description": "Examine the map of the world to discover what the aliens are planning."
    },
    {
        "command": {
            "commandType": "GET",
            "entity-id": "staff"
        },
        "onCompleteMessage": "Congratulations you have defeated the Captain and have collected his sacred staff.",
        "description": "Collect the sacred staff."
    },
    {
        "command": {
            "commandType": "LOOK",
            "entity-id": "windowc"
        },
        "onCompleteMessage": "You have completed one of the objectives: isn't space beautiful...",
        "description": "Look out the window of Airlock C."
    },
    {
        "command": {
            "commandType": "USE",
            "entity-id": "keyboard"
        },
        "onCompleteMessage": "You have written a message and sent it back to Earth.",
        "description": "Write and send a message."
    }],
    
    "player": {
		"name": "Player 1",
		"description": "cool as",
		"uniqueId": "player",
        
        "currentRoom": "cargobayb",
		"visitedRooms":[],
        "inventory": {
            "items": [],
            "maxItems": 5
        },
        "currentHealth": 80,
        "maxHealth": 100
    },
    
    "rooms": [{
        "inventory": {
            "items": [{
                "description": "Small dagger with a smooth handle",
                "name": "Knife",
                "maxDamage": 7,
                "type": "weapon",
                "minDamage": 3,
				"easeOfUse": 50,
                "uniqueId": "knife",
                "transformsTo": {
		                "description": "A sharpened rusty spoon",
		                "name": "Spoon",
						"type": "weapon",
		                "uniqueId": "spoon",
						"maxDamage": 1,
						"minDamage": 1,
						"easeOfUse": 1
                }
            },
            {
                "usable": false,
                "description": "A stack of heavy boxes within the cargo bay",
                "name": "Heavy Boxes",
                "carriable": false,
                "type": "item",
                "uniqueId": "boxes"
            },
            {
                "usable": false,
                "description": "Sharp shards of broken glass",
                "name": "Broken Glass",
                "carriable": true,
                "type": "item",
                "uniqueId": "brokenglass"
            }],
            "characters": [{
                "narratives": ["Go to the Shield Room and deactivate the fuel cells so the shield is down.", "Watch out for the aliens, they will attack!", "Go to the Communications Room and send a message back to Earth.", "Kill the Captain in the Bridge and collect his Staff."],
                "inventory": {
                    "items": []
                },
                "description": "Wounded soldier found on the ship",
                "name": "Soldier",
                "type": "friendly",
                "uniqueId": "soldier"
            }]
        },
        "description": "Dark room, with boxes lying about, a wounded man is in the corner leaning against the wall, blood seeping from his leg.",
        "name": "Cargo Bay B",
        "adjoiningRooms": ["cargobaya", "cargobayc", "engineroom", "kitchen"],
        "uniqueId": "cargobayb"
    },
	{
        "inventory": {
            "items": [{
				"usable": false,
				"description": "A yellow banana",
				"name": "Banana",
				"carriable": true,
				"type": "item",
				"uniqueId": "banana2",
                "transformsTo": {
					"usable": false,
					"description": "A yellow peeled banana",
					"name": "Peeled Banana",
					"carriable": true,
					"type": "item",
					"uniqueId": "banana2peeled"
                }
            }],
            "characters": [{
                "wieldedWeapon": "meatcleaver",
                "narratives": ["ARGHHH DIE!"],
                "inventory": {
                    "items": [{
                        "description": "Meat Cleaver",
                        "name": "Meat Cleaver",
                        "maxDamage": 20,
                        "type": "weapon",
                        "minDamage": 10,
						"easeOfUse": 18,
                        "uniqueId": "meatcleaver"
                    }]
                },
                "description": "The chef of the space ship",
                "name": "Chef",
                "type": "hostile",
                "attackProbability": 0.9,
                "currentHealth": 20,
                "maxHealth": 95,
                "uniqueId": "chef"
            }]
        },
        "description": "A small kitchen with an angry chef.",
        "name": "Kitchen",
        "adjoiningRooms": ["cargobayb"],
        "uniqueId": "kitchen"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "revolver",
                "narratives": ["Prepare to feel pain!"],
                "inventory": {
                    "items": [{
                        "description": "Revolver",
                        "name": "Revolver",
                        "maxDamage": 20,
                         "type": "weapon",
                        "minDamage": 10,
						"easeOfUse": 18,
                        "uniqueId": "revolver"
                    }]
                },
                "description": "Crew Member",
                "name": "Crew Member",
                "currentHealth": 20,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.2,
                "uniqueId": "crewmember1"
            }]
        },
        "description": "One lightbulb dislaying a completely empty room, with doors at either end.",
        "name": "Cargo Bay A",
        "adjoiningRooms": ["cargobayb", "dockingbaya"],
        "uniqueId": "cargobaya"
    },
    {
        "inventory": {
            "items": [{
                "usable": true,
                "description": "A health boost for you.",
                "name": "Red Medicine",
                "carriable": true,
                "type": "medicine",
                "uniqueId": "medicine4"
            }],
            "characters": [{
                "wieldedWeapon": "spanner",
                "narratives": ["You'll never make it off this ship alive..."],
                "inventory": {
                    "items": [{
                        "description": "Spanner",
                        "name": "Spanner",
                        "maxDamage": 5,
                        "type": "weapon",
                        "minDamage": 1,
						"easeOfUse": 35,
                        "uniqueId": "spanner"
                    }]
                },
                "description": "A member of the crew of the ship.",
                "name": "Crew Member",
                "currentHealth": 15,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.4,
                "uniqueId": "crewmember2"
            }]
        },
        "description": "Full of crates that have yet to be moved to the Cargo Bay's and opposite a sign on the door to the Airlock reads 'DO NOT ENTER'...",
        "name": "Docking Bay A",
        "adjoiningRooms": ["cargobaya", "airlocka"],
        "uniqueId": "dockingbaya"
    },
    {
        "inventory": {
            "items": [{
                        "attackable": false,
                        "usable": false,                       
                        "description": "A breathtaking view out into space showing the Earth below.",
                        "name": "Window",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "windowa"
                    }],
            "characters": []
        },
        "description": "A very cold room with a very strong door at one end, that does not look like it should be opened.",
        "name": "Airlock A",
        "adjoiningRooms": ["dockingbaya", "flightdecka", "space"],
        "uniqueId": "airlocka"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "metalbar",
                "narratives": [],
                "inventory": {
                    "items": [{
                        "description": "Long strong metal bar",
                        "name": "Metal Bar",
                        "maxDamage": 8,
                          "type": "weapon",
                        "minDamage": 4,
						"easeOfUse": 30,
                        "uniqueId": "metalbar"
                    }]
                },
                "description": "Fighter Pilot",
                "name": "Fighter Pilot",
                "currentHealth": 25,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.6,
                "uniqueId": "fighterpilot2"
            }, {
                "wieldedWeapon": "flaregun",
                "narratives": [],
                "inventory": {
                    "items": [{
                        "description": "Flaregun",
                        "name": "Flaregun",
                        "maxDamage": 10,
                        "type": "weapon",
                        "minDamage": 1,
						"easeOfUse": 15,
                        "uniqueId": "flaregun"
                    }]
                },
                "description": "Co-Pilot",
                "name": "Co-Pilot",
                "currentHealth": 25,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.1,
                "uniqueId": "copilot"
            }]
        },
        "description": "An alien fighter jet is filling up with fuel, and opposite a sign on the door to the Airlock reads 'DO NOT ENTER'...",
        "name": "Flight Deck A",
        "adjoiningRooms": ["airlocka", "weaponsbaya", "crewquartersa"],
        "uniqueId": "flightdecka"
    },
    {
        "inventory": {
            "items": [{
                        "description": "Sniper Rifle",
                        "name": "Sniper Rifle",
                        "maxDamage": 35,
                        "type": "weapon",
                        "minDamage": 25,
						"easeOfUse": 5,
                        "uniqueId": "sniperrifle"
                    },
                    {
                        "description": "Machine Gun",
                        "name": "Machine Gun",
                        "maxDamage": 40,
                        "type": "weapon",
                        "minDamage": 5,
						"easeOfUse": 12,
                        "uniqueId": "machingun"
                    },
                    {
                        "description": "Sonic Blaster",
                        "name": "Sonic Blaster",
                        "maxDamage": 20,
                        "type": "weapon",
                        "minDamage": 10,
						"easeOfUse": 30,
                        "uniqueId": "sonic blaster"
                    }],
            "characters": []
        },
        "description": "Racks of different weapons line the walls, with even more held on central tables.",
        "name": "Weapons Bay A",
        "adjoiningRooms": ["flightdecka", "shieldroom"],
        "uniqueId": "weaponsbaya"
    },
    {
        "inventory": {
            "items": [{
                        "usable": true,
                        "description": "Fuel cell for the shield of the ship.",
                        "name": "Shield Fuel Cell 1",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "fuelcell1"
                    }, {
                        "usable": true,
                        "description": "Fuel cell for the shield of the ship.",
                        "name": "Shield Fuel Cell 2",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "fuelcell2"
                    }, {
                        "usable": true,
                        "description": "Fuel cell for the shield of the ship.",
                        "name": "Shield Fuel Cell 3",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "fuelcell3"
                    }, {
                        "usable": true,
                        "description": "Fuel cell for the shield of the ship.",
                        "name": "Shield Fuel Cell 4",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "fuelcell4"
                    }],
            "characters": [{
                "wieldedWeapon": "handgun",
                "narratives": ["You'll never bring this ship down!"],
                "inventory": {
                    "items": [{
                        "description": "Hand-gun",
                        "name": "Hand-gun",
                        "maxDamage": 15,
                        "type": "weapon",
                        "minDamage": 5,
						"easeOfUse": 15,
                        "uniqueId": "handgun"
                    }]
                },
                "description": "Angry Crew Member",
                "name": "Crew Member",
                "currentHealth": 25,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.8,
                "uniqueId": "crewmember7"
            }]
        },
        "description": "A large room with four pillars around the centre, each with a bright fluorescent tube in the middle, these are the power cells for the ships shield.",
        "name": "Shield Room",
        "adjoiningRooms": ["weaponsbaya", "weaponsbayb", "sensorbay"],
        "uniqueId": "shieldroom"
    },
    {
        "inventory": {
            "items": [],
            "characters": []
        },
        "description": "Hammocks hang from the ceiling, each one in it's own little area with cupboards below it for each member of the crew.",
        "name": "Crew Quarters A",
        "adjoiningRooms": ["flightdecka", "engineroom", "galley"],
        "uniqueId": "crewquartersa"
    },
    {
        "inventory": {
            "items": [{
                        "description": "Assault Rifle",
                        "name": "Assault Rifle",
                        "maxDamage": 20,
                        "type": "weapon",
                        "minDamage": 5,
						"easeOfUse": 30,
                        "uniqueId": "assualtrifle"
                    },
                    {
                        "description": "Pistol",
                        "name": "Pistol",
                        "maxDamage": 15,
                        "type": "weapon",
                        "minDamage": 5,
						"easeOfUse": 20,
                        "uniqueId": "pistol"
                    },
                    {
                        "description": "Shotgun",
                        "name": "Shotgun",
                        "maxDamage": 25,
                        "type": "weapon",
                        "minDamage": 1,
						"easeOfUse": 20,
                        "uniqueId": "shotgun"
                    }],
            "characters": []
        },
        "description": "Racks of different weapons line the walls, with even more on tables in the centre. The vast majority of the weapons are locked in and can't be removed.",
        "name": "Weapons Bay B",
        "adjoiningRooms": ["shieldroom", "flightdeckb"],
        "uniqueId": "weaponsbayb"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "dagger",
                "narratives": ["Prepare to die!"],
                "inventory": {
                    "items": [{
                        "description": "Dagger",
                        "name": "Dagger",
                        "maxDamage": 10,
                        "type": "weapon",
                        "minDamage": 3,
						"easeOfUse": 45,
                        "uniqueId": "dagger"
                    }]
                },
                "description": "Fighter Pilot",
                "name": "Fighter Pilot",
                "currentHealth": 25,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.6,
                "uniqueId": "fighterpilot1"
            }]
        },
        "description": "A small ship is preparing for flight, on the opposite side of the room, a sign on the door to the Airlock reads 'DO NOT ENTER'... ",
        "name": "Flight Deck B",
        "adjoiningRooms": ["weaponsbayb", "airlockb", "crewquartersb"],
        "uniqueId": "flightdeckb"
    },
    {
        "inventory": {
            "items": [{
                        "attackable": false,
                        "usable": false,
                        "description": "A breathtaking view out into space showing the Earth below.",
                        "name": "Window",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "windowb"
                    }],
            "characters": []
        },
        "description": "A very cold room with a very strong door at one end, that does not look like it should be opened.",
        "name": "Airlock B",
        "adjoiningRooms": ["flightdeckb", "dockingbayb", "space"],
        "uniqueId": "airlockb"
    },
    {
        "inventory": {
            "items": [],
            "characters": []
        },
        "description": "A couple of unused small cargo ships are here waiting to be mended, and opposite a sign on the door to the Airlock reads 'DO NOT ENTER'...",
        "name": "Docking Bay B",
        "adjoiningRooms": ["airlockb", "cargobayc"],
        "uniqueId": "dockingbayb"
    },{
        "inventory": {
            "items": [{
                "usable": false,
                "description": "A pile of empty crates within the cargo bay",
                "name": "Empty Crates",
                "carriable": false,
                "type": "item",
                "uniqueId": "emptycrates"
            }],
            "characters": []
        },
        "description": "One lightbulb dislaying crates all around, and doors at either end.",
        "name": "Cargo Bay C",
        "adjoiningRooms": ["dockingbayb", "cargobayb"],
        "uniqueId": "cargobayc"
    },
    {
        "inventory": {
            "items": [{
                        "description": "Grimy metal tool",
                        "name": "Wrench",
                        "maxDamage": 5,
                        "type": "weapon",
                        "minDamage": 4,
						"easeOfUse": 35,
                        "uniqueId": "wrench"
                    }],
            "characters": []
        },
        "description": "Very hot and loud with large machinery moving dangerously. A pile of tools lie to the left.",
        "name": "Engine Room",
        "adjoiningRooms": ["cargobayb", "crewquartersa", "crewquartersb", "nuclearpowercell"],
        "uniqueId": "engineroom"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "bentmetalbar",
                "narratives": [],
                "inventory": {
                    "items": [{
                        "description": "Long, bent metal bar",
                        "name": "Bent Metal Bar",
                        "maxDamage": 6,
                        "type": "weapon",
                        "minDamage": 3,
						"easeOfUse": 25,
                        "uniqueId": "bentmetalbar"
                    }]
                },
                "description": "Crew Member",
                "name": "Crew Member 1",
                "currentHealth": 15,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.4,
                "uniqueId": "crewmember6"
            },
            {
                "wieldedWeapon": "plasmagun",
                "narratives": [],
                "inventory": {
                    "items": [{
                        "description": "Plasmagun",
                        "name": "Plasmagun",
                        "maxDamage": 30,
                        "type": "weapon",
                        "minDamage": 20,
						"easeOfUse": 35,
                        "uniqueId": "plasmagun"
                    }]
                },
                "description": "Crew Member",
                "name": "Crew Member 2",
                "currentHealth": 15,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.3,
                "uniqueId": "crewmember3"
            }]
        },
        "description": "Hammocks hang from the ceiling, each one in it's own little area with cupboards below it for each member of the crew.",
        "name": "Crew Quarters B",
        "adjoiningRooms": ["flightdeckb", "engineroom", "sickbay"],
        "uniqueId": "crewquartersb"
    },
    {
        "inventory": {
            "items": [],
            "characters": []
        },
        "description": "Circular room surrounding a nuclear reactor, rows and rows of devices are around the edge that seem to be controlling the environment of the room.",
        "name": "Nuclear Power Cell",
        "adjoiningRooms": ["engineroom"],
        "uniqueId": "nuclearpowercell"
    },
    {
        "inventory": {
            "items": [],
            "characters": []
        },
        "description": "Tiny room with a bright blue circle outline on the floor, you stand in the circle to exit to the Navigation Room.",
        "name": "Teleport Bay A",
        "adjoiningRooms": ["navigationroom"],
        "uniqueId": "teleportbaya"
    },
    {
        "inventory": {
            "items": [{
                "usable": true,
                "description": "Curtain hanging underneath the worktop hiding something from view.",
                "name": "Curtain",
                "carriable": false,
                "type": "item",
                "uniqueId": "curtain"
            },
            {
                "usable": true,
                "description": "Hay and grains",
                "name": "Llama Food",
                "carriable": false,
                "attackable": false,
                "type": "item",
                "uniqueId": "llamafood"
            }],
            "characters": [{
                "narratives": ["Squeak"],
                "inventory": {
                    "items": []
                },
                "description": "baby llama in a little box with bedding for it",
                "name": "Baby Llama",
                "type": "friendly",
                "uniqueId": "babyllama"
            }]
        },
        "description": "Long room with a kitchen area at one end, quiet squeaks are coming from behind a curtain underneath the worktop...",
        "name": "Galley",
        "adjoiningRooms": ["teleportbaya", "hologramroom", "crewquartersa"],
        "uniqueId": "galley"
    },
    {
        "inventory": {
            "items": [],
            "characters": []
        },
        "description": "Tiny room with a bright blue circle outline on the floor, you stand in the circle to exit to Docking Bay B.",
        "name": "Teleport Bay B",
        "adjoiningRooms": ["dockingbayb"],
        "uniqueId": "teleportbayb"
    },
    {
        "inventory": {
            "items": [{
                "usable": true,
                "description": "A health boost for you.",
                "name": "Blue Medicine",
                "carriable": true,
                "type": "medicine",
                "uniqueId": "medicine1"
            }],
            "characters": []
        },
        "description": "A row of beds with medical equipment bedside them",
        "name": "Sick Bay",
        "adjoiningRooms": ["teleportbayb", "researchlab", "crewquartersb"],
        "uniqueId": "sickbay"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "combatknife",
                "narratives": ["Prepare to feel pain!"],
                "inventory": {
                    "items": [{
                        "description": "Combat Knife",
                        "name": "Combat Knife",
                        "maxDamage": 12,
                        "type": "weapon",
                        "minDamage": 3,
						"easeOfUse": 45,
                        "uniqueId": "combatknife"
                    }]
                },
                "description": "Higher Level Crew Member",
                "name": "Crew Member",
                "currentHealth": 30,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.6,
                "uniqueId": "highercrewmember1"
            }]
        },
        "description": "Large screens line the walls, some showing multiple rooms of the ship, this must be how they track the crew on the ship, I best be careful",
        "name": "Sensor Bay",
        "adjoiningRooms": ["teleportcontrolroom", "shieldroom"],
        "uniqueId": "sensorbay"
    },
    {
        "inventory": {
            "items": [{
                "usable": true,
                "description": "A health boost for you.",
                "name": "Green Medicine",
                "carriable": true,
                "type": "medicine",
                "uniqueId": "medicine5"
            }],
            "characters": []
        },
        "description": "Workbenches are covered in chemical equipment, from bunsen burners to beakers. A table holds containers labelled with different acids.",
        "name": "Research Lab",
        "adjoiningRooms": ["sickbay", "engineering"],
        "uniqueId": "researchlab"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "hammer",
                "narratives": [],
                "inventory": {
                    "items": [{
                        "description": "Hammer",
                        "name": "Hammer",
                        "maxDamage": 12,
                        "type": "weapon",
                        "minDamage": 7,
						"easeOfUse": 35,
                        "uniqueId": "hammer"
                    }]
                },
                "description": "Crew Member",
                "name": "Crew Member 1",
                "currentHealth": 30,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.4,
                "uniqueId": "crewmember4"
            },
            {
                "wieldedWeapon": "rustymetalbar",
                "narratives": ["Prepare to die!"],
                "inventory": {
                    "items": [{
                        "description": "Long strong metal bar",
                        "name": "Rusty Metal Bar",
                        "maxDamage": 8,
                        "type": "weapon",
                        "minDamage": 4,
						"easeOfUse": 28,
                        "uniqueId": "rustymetalbar"
                    }]
                },
                "description": "Crew Member",
                "name": "Crew Member 2",
                "currentHealth": 30,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.6,
                "uniqueId": "crewmember5"
            }]
        },
        "description": "A workbench with a half built engine are there, with lots of tools lying around on the floor.",
        "name": "Engineering",
        "adjoiningRooms": ["researchlab", "bridge"],
        "uniqueId": "engineering"
    },
    {
        "inventory": {
            "items": [{
	            "carriable": true,
	            "usable": true,
                "description": "A health boost for you.",
                "name": "Yellow Medicine",
                "type": "medicine",
                "uniqueId": "medicine3"
            }],
            "characters": []
        },
        "description": "A giant hologram of the ship is in the centre of the room, you can walk right through it.",
        "name": "Hologram Room",
        "adjoiningRooms": ["galley", "bridge", "teleportcontrolroom"],
        "uniqueId": "hologramroom"
    },
    {
        "inventory": {
            "items": [],
            "characters": []
        },
        "description": "Just one control panel split into two sections that are completely identical.",
        "name": "Teleport Control Room",
        "adjoiningRooms": ["hologramroom", "bridge", "sensorbay"],
        "uniqueId": "teleportcontrolroom"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "lasergun",
                "narratives": ["I can smell your fear from here..."],
                "inventory": {
                    "items": [{
                        "description": "Laser-gun",
                        "name": "Laser-gun",
                        "maxDamage": 15,
                        "type": "weapon",
                        "minDamage": 5,
						"easeOfUse": 40,
                        "uniqueId": "lasergun"
                    }, {
                        "attackable": false,
                        "usable": false,
                        "description": "Captain's Staff",
                        "name": "Staff",
                        "carriable": true,
                        "type": "item",
                        "uniqueId": "staff"
                    }]
                },
                "description": "Biggest of all the aliens, wearing armour",
                "name": "Captain",
                "currentHealth": 80,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.95,
                "uniqueId": "captain"
            }]
        },
        "description": "The centre of the ship, two levels with an enormous sphere in the middle of the floor. The sphere is pulsing and slightly glowing red in colour, it is surrounded by glass. Looking up there are steps to access the rooms on the upper levels of the ship.",
        "name": "Bridge",
        "adjoiningRooms": ["engineering", "hologramroom", "teleportcontrolroom", "chartroom", "navigationroom", "communicationsroom", "briefingroom"],
        "uniqueId": "bridge"
    },
    {
        "inventory": {
            "items": [{
                "usable": false,
                "description": "Map of Europe, certain cities marked with a red X.",
                "name": "Map of Europe",
                "carriable": true,
                "type": "item",
                "uniqueId": "mapeurope"
            }, {
                "usable": false,
                "description": "Map of Asia, certain cities marked with a red X.",
                "name": "Map of Asia",
                "carriable": true,
                "type": "item",
                "uniqueId": "mapasia"
            }, {
                "usable": false,
                "description": "Map of North and South America, certain cities marked with a red X.",
                "name": "Map of North and South America",
                "carriable": true,
                "type": "item",
                "uniqueId": "mapamerica"
            }, {
                "usable": false,
                "description": "Map of the entire world with a giant red X through it!",
                "name": "Map of the world",
                "carriable": true,
                "type": "item",
                "uniqueId": "worldmap"
            }],
            "characters": []
        },
        "description": "Large table takes up most of the space with three extremely large maps draped across it, other rolled up charts and documents line the walls. There are blueprints to the smaller alien aircraft on the wall.",
        "name": "Chart Room",
        "adjoiningRooms": ["bridge", "navigationroom", "observationdeck"],
        "uniqueId": "chartroom"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "flamethrower",
                "narratives": ["You've got no chance against me!"],
                "inventory": {
                    "items": [{
                        "description": "Flamethrower",
                        "name": "Flamethrower",
                        "maxDamage": 30,
                        "type": "weapon",
                        "minDamage": 1,
						"easeOfUse": 10,
                        "uniqueId": "flamethrower"
                    }]
                },
                "description": "Higher Level Crew Member",
                "name": "Crew Member",
                "currentHealth": 30,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.5,
                "uniqueId": "highercrewmember4"
            }]
        },
        "description": "A large radar flashing red and green, showing the aircraft from the ship attacking cities on Earth, with a few fighter jets putting up a defense. To one side a sign on the door to the Airlock reads 'DO NOT ENTER'...",
        "name": "Navigation Room",
        "adjoiningRooms": ["bridge", "chartroom", "airlockd", "briefingroom"],
        "uniqueId": "navigationroom"
    },
    {
        "inventory": {
            "items": [{
                        "usable": true,
                        "description": "Large section of the wall protruding outwards covered in an array of buttons all different shapes, sizes and colours, two large screens hang above. A bomb lies in the centre of the board, between the screens.",
                        "name": "Control Board",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "communicationspanel"
                    },
                    {
                        "usable": true,
                        "description": "Keyboard with many additional keys covered in strange symbols and a lever to send any messages.",
                        "name": "Keyboard",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "keyboard"
                    }],
            "characters": []
        },
        "description": "Four large control panels are situated around a statue of a llama in the centre.",
        "name": "Communications Room",
        "adjoiningRooms": ["bridge", "flightcontrolroom"],
        "uniqueId": "communicationsroom"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "rifle",
                "narratives": ["I'll teach you the meaning of the word pain..."],
                "inventory": {
                    "items": [{
                        "description": "Rifle",
                        "name": "Rifle",
                        "maxDamage": 22,
                        "type": "weapon",
                        "minDamage": 15,
						"easeOfUse": 15,
                        "uniqueId": "rifle"
                    }]
                },
                "description": "Higher Level Crew Member",
                "name": "Crew Member",
                "currentHealth": 30,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.2,
                "uniqueId": "highercrewmember3"
            }]
        },
        "description": "A central table dominates the space with chairs at every possible interval, hovering above the centre is a hologram of Earth, which spins slowly.",
        "name": "Briefing Room",
        "adjoiningRooms": ["bridge", "captainsquarters", "navigationroom"],
        "uniqueId": "briefingroom"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "grenades",
                "narratives": ["You should've gotten out while you still could."],
                "inventory": {
                    "items": [{
                        "description": "Grenades",
                        "name": "Grenades",
                        "maxDamage": 50,
                        "type": "weapon",
                        "minDamage": 1,
						"easeOfUse": 5,
                        "uniqueId": "grenades"
                    }]
                },
                "description": "Higher Level Crew Member",
                "name": "Crew Member",
                "currentHealth": 30,
                "maxHealth": 95,
                "type": "hostile",
                "attackProbability": 0.75,
                "uniqueId": "highercrewmember2"
            }]
        },
        "description": "A wall of glass showing the Earth below, where you can make out some of the continents. Rows of seating line the opposite wall.",
        "name": "Observation Deck",
        "adjoiningRooms": ["chartroom", "weaponscontrolroom", "flightcontrolroom"],
        "uniqueId": "observationdeck"
    },
    {
        "inventory": {
            "items": [],
            "characters": []
        },
        "description": "There are consoles screens everywhere, with crosshairs focusing on major cities, and one crosshair viewing a lady in a shower.",
        "name": "Weapons Control Room",
        "adjoiningRooms": ["observationdeck"],
        "uniqueId": "weaponscontrolroom"
    },
    {
        "inventory": {
            "items": [{
                        "attackable": false,
                        "usable": false,
                        "description": "A breathtaking view out into space showing the Earth below.",
                        "name": "Window",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "windowc"
                    }],
            "characters": []
        },
        "description": "A very cold room with a very strong door at one end, that does not look like it should be opened.",
        "name": "Airlock C",
        "adjoiningRooms": ["flightcontrolroom", "space"],
        "uniqueId": "airlockc"
    },
    {
        "inventory": {
            "items": [],
            "characters": [{
                "wieldedWeapon": "vacuumweapon",
                "narratives": ["I will teach the meaning of the word fear..."],
                "inventory": {
                    "items": [{
                        "description": "Nothing",
                        "name": "Vacuum",
                        "maxDamage": 10000,
                        "type": "weapon",
                        "minDamage": 10000,
						"easeOfUse": 10000,
                        "uniqueId": "vacuumweapon"
                    }]
                },
                "description": "no air",
                "name": "Space",
                "currentHealth": 10000,
                "maxHealth": 10000,
                "type": "hostile",
                "attackProbability": 1,
                "uniqueId": "vacuum"
            }]
        },
        "description": "Nothing but the ship gradually getting further away until...",
        "name": "Space",
        "adjoiningRooms": [],
        "uniqueId": "space"
    },
    {
        "inventory": {
            "items": [],
            "characters": []
        },
        "description": "Four main panels, covered in flashing lights and buttons, to one side a sign on the door to the Airlock reads 'DO NOT ENTER'...",
        "name": "Flight Control Room",
        "adjoiningRooms": ["observationdeck", "communicationsroom", "airlockc"],
        "uniqueId": "flightcontrolroom"
    },
    {
        "inventory": {
            "items": [{
                        "attackable": false,
                        "usable": false,
                        "description": "A breathtaking view out into space showing the Earth below.",
                        "name": "Window",
                        "carriable": false,
                        "type": "item",
                        "uniqueId": "windowd"
                    }],
            "characters": []
        },
        "description": "A very cold room with a very strong door at one end, that does not look like it should be opened.",
        "name": "Airlock D",
        "adjoiningRooms": ["navigationroom", "space"],
        "uniqueId": "airlockd"
    },
    {
        "inventory": {
            "items": [{
	            "carriable": true,
	            "usable": true,
                "description": "A health boost for you.",
                "name": "Orange Medicine",
                "type": "medicine",
                "uniqueId": "medicine2"
            }],
            "characters": []
        },
        "description": "This is the Captain's Quarters, important documents spread the desk and many tasks are running on the computer. There is also 3 blow-up llama dolls bulging out the wardrobe",
        "name": "Captains Quarters",
        "adjoiningRooms": ["briefingroom"],
        "uniqueId": "captainsquarters"
    }]
}
