import React, { Component } from 'react';
import logo from './logo.png'

export default class Footer extends Component {
    render() {

        var date = new Date();
        var today = date.getFullYear();  

        return (
            <div>
                <nav className="navbar navbar-expand-lg navbar-light bg-light rounded mt-3">        
                    <div className="collapse navbar-collapse justify-content-md-center" id="navbarsExample10">
                        <ul className="navbar-nav">
                            <li className="nav-item active">
                            <a className="nav-link" href="#"><img src={logo} style={{width:'30px'}} /> Fire Alarm System | © {today} Copyright Reserved</a>
                            </li>                            
                        </ul>
                    </div>
                </nav>
            </div>
        )
    }
}
