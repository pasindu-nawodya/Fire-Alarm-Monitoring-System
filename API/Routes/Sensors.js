const express = require('express');
const router = express.Router();
const SensorDetails = require('../Models/SensorDetails');

//get all sensor
router.get('/',async (req,res)=>{
    try{
        const sensors = await SensorDetails.find();
        res.json(sensors);
    }catch(err){
        res.json({message:err})
    }
})

//submit sensor details
router.post('/',(req,res)=>{
    const sensor = new SensorDetails({
        sensorid:req.body.sensorid,
        floor:req.body.floor,
        room:req.body.room,
        colevel:req.body.colevel,
        smokelevel:req.body.smokelevel

    });

    console.log(req.body);

    sensor.save()
    .then(data=>{
        res.json(data);
    })
    .catch(err=>{
        res.json({message:err})
    })
})

//specific sensor details
router.get('/:sensorid',async (req,res)=>{
    try{
        const specificSensor = await SensorDetails.findById(req.params.sensorid);
        res.json(specificSensor);
    }catch(err){
        res.json({message:err});
    }
})

//delete sensor
router.delete('/:sensorid',async (req,res)=>{
    try{
        const deleteSensor =await SensorDetails.remove({_id: req.params.sensorid});
        res.json(deleteSensor);
    }catch(err){
        res.json({message:err});
    }
})

//update sensor
router.post('/:sensorid',async (req,res)=>{
    try{
        const updateSensor =await SensorDetails.updateOne({_id:req.params.sensorid},
            {$set : {sensorid:req.body.sensorid,
                    floor:req.body.floor,
                    room:req.body.room,
                    colevel:req.body.colevel,
                    smokelevel:req.body.smokelevel}});
        
            res.json(updateSensor);


    }catch(err){
        res.json({message:err});
    }
})



module.exports=router;