import React,{useEffect,useState} from 'react';
import Navbar from './component/Navbar/Navbar'
import MainContent from './component/MainContent/MainContent'
import SensorDetails from './component/SensorDetails/SensorDetails'
import Footer from './component/Footer/Footer'

const App = ()=> {

  const[sensors,setSensors]= useState([]);

  useEffect(()=>{
    
    setInterval(function(){
      getSensors();
    },5000);
   
  },[]);

  const getSensors = async ()=>{
    const response = await fetch('http://localhost:4000/sensors');
    const data = await response.json();    
    setSensors(data);
  }

  return (
    <div className="App">
      <Navbar />
      <MainContent sensorDetail={sensors} key={sensors._id}/>
      <SensorDetails sensorDetail={sensors} key={sensors._id}/>
      <Footer />
    </div>
  );
}

export default App;
