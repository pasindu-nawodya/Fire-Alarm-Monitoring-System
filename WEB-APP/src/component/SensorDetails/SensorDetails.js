import React from 'react'

const SensorDetails = ({sensorDetail}) => {
    //"btn btn-danger"
        return (
            
            <div className="container">

                <center><h2>Fire Alarms Details</h2></center><br />
                <table className="table table-hover border-rounded mb-3" style={{textAlign:'center'}}>
                    <thead className="thead-dark">
                    <tr>
                        <th scope="col">Sensor Id</th>
                        <th scope="col">Floor</th>
                        <th scope="col">Room</th>
                        <th scope="col">CO<sub> 2</sub> Level</th>
                        <th scope="col">Smoke Level</th>
                        <th scope="col">Alarm Status</th>
                    </tr>
                    </thead>
                    <tbody>
                        {sensorDetail.map(sensor=>(
                            <tr>
                                <td>{sensor.sensorid}</td>
                                <td>{sensor.floor} Floor</td>
                                <td>{sensor.room} Room</td>
                                <td>{sensor.colevel}</td>
                                <td>{sensor.smokelevel}</td>
                                <td><button className={(sensor.colevel>5 || sensor.smokelevel>5)?"btn btn-danger":"btn btn-success"}>
                                {(sensor.colevel>5 || sensor.smokelevel>5)?"On":"Off"}
                                </button></td>
                            </tr> 
                        ))}                        
                    </tbody>
                 </table>
            </div>
        )
    
}

export default SensorDetails;
