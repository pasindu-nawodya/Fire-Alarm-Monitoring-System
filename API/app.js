const express = require('express');
const app = express();
const mongoose = require('mongoose');
const bodyparser = require('body-parser');
const cors = require('cors');
require('dotenv/config');


//import routes
const sensorRoute = require('./Routes/Sensors');
const userRoute = require('./Routes/Users');

//middleware
app.use(bodyparser.json());
app.use(cors());
app.use('/sensors',sensorRoute);
app.use('/users',userRoute);

//db
mongoose.connect(
    process.env.DB_CONNECTION,
    {useNewUrlParser:true,useUnifiedTopology: true},
    ()=>console.log('Database connected!')
)

app.listen(4000);