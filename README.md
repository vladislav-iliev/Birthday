# Birthday
## Overview
Receive a kid's [Name, Date of Birth, Theme] via WebSocket. Then, show a Happy Birthday screen according to company design. Allow selection of avatar via Camera or Gallery.

[<img src="media/screenshot.png" alt="media/screenshot" height="468" width="216">](https://github.com/vladislav-iliev/home-task-birthday/blob/main/media/screenshot.png)

## Videos
https://github.com/vladislav-iliev/home-task-birthday/blob/main/media/demo.mp4

## Design
[<img src="media/spec.png" alt="/media/spec" height="468" width="216">](https://github.com/vladislav-iliev/home-task-birthday/blob/main/media/spec.png)

## Inside

Upon app start, three components are started - a Repository for the WebSocket 'networking/Repository' to handle  the connections, a Repository for Text (the parsed server messages are referred to as a kid's 'Text') 'kid/text/Repository',  which also has a reference to the Networking Repository, since it consumes the flow of messages, and a Repository for the avatar  'kid/avatar/Repository'.

The beginning destination 'screens/connect/ConnectScreen' has its ViewModel accept all three aforementioned components, even though not all three are used - this is to force Dagger-Hilt to instantiate them from the beginning of the app.

The combination of a kid's Text (name, age and theme) and Avatar form the kid's state 'kid/State'. Also 
in the state is a flag showing if the server connection is active (a boolean value isActive). When isActive goes false,
 we go back to the connect screen, since the kid is offline.
