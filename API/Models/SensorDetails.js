const mongoose = require('mongoose');

const PostSchema = mongoose.Schema({
    sensorid:{type:Number,required:true},
    floor:{type:Number,required:true},
    room:{type:Number,required:true},
    colevel:{type:Number,default:0},
    smokelevel:{type:Number,default:0}
});

module.exports=mongoose.model('SensorDetails',PostSchema);