import React from 'react';
import logo from './fire.png'

const MainContent = ({sensorDetail}) => {

        //check one of fire active
        var status = false;

        {sensorDetail.map(sensor=>{
            if(sensor.colevel>5 || sensor.smokelevel>5){
                status = true;
            }
        })} 
    
        return (
            <div>
                <main role="main" >
                    <section className="jumbotron text-center"  style={{height:'100%'}}>
                        <div className="container">
                        <div className="row">
                            <div className="col-sm">
                                <img src={logo} style={{width:'50%'}} />
                            </div>
                            <div className="col-lg">
                            <br />
                            <br />
                            <h1><b>Fire Alarm System</b></h1>
                            <p className="lead text-muted">Use Technology for Safety | Get Know Before Burn</p>
                            <br />
                            <p>
                            <b>Current Status : </b>
                            <a href="#" className={(status)?"btn btn-danger ml-3 my-2":"btn btn-success ml-3 my-2"}>
                            {(status)?"Danger : Alarm Activated":"Normal"}
                            </a>
                            </p>
                            </div>
                            </div>
                        </div>
                    </section>
                </main>
            </div>
        )
    
}

export default MainContent;
