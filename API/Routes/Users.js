const express = require('express');
const router = express.Router();
const UserDetails = require('../Models/UserDetails');

//get all users
router.get('/',async (req,res)=>{
    try{
        const users = await UserDetails.find();
        res.json(users);
    }catch(err){
        res.json({message:err})
    }
})

//submit user details
router.post('/',(req,res)=>{
    const user = new UserDetails({
        userid:req.body.userid,
        username:req.body.username,
        password:req.body.password
    });

    console.log(req.body);

    user.save()
    .then(data=>{
        res.json(data);
    })
    .catch(err=>{
        res.json({message:err})
    })
})

//view users
router.get('/:userid',async (req,res)=>{
    try{
        const specificUser = await UserDetails.findById(req.params.userid);
        res.json(specificUser);
    }catch(err){
        res.json({message:err});
    }
})



module.exports=router;