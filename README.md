# home-task-birthday

## State
Bonus part done. Tests included (probably cover most points of interest). Tests are being added for the Bonus part.

## Videos
https://github.com/vladislav-iliev/home-task-birthday/blob/main/media/demo.mp4

## Brief overview
Upon app start, three components are started - a Repository for the WebSocket 'networking/Repository' to handle 
the connections, a Repository for Text (the parsed server messages are referred to as a kid's 'Text') 'kid/text/Repository', 
which also has a reference to the Networking Repository, since it consumes the flow of messages, and a Repository the avatar 
'kid/avatar/Repository'.

The beginning destination 'screens/connect/ConnectScreen' has its ViewModel accept all three aforementioned components,
even though not all three are used - this is to force Dagger-Hilt to instantiate them from the beginning of the app.

The combination of a kid's Text (name, age and theme) and Avatar form the kid's state 'kid/State'. Also 
in the state is a flag showing if the server connection is active (a boolean value isActive). When isActive goes false,
 we go back to the connect screen, since the kid is offline.