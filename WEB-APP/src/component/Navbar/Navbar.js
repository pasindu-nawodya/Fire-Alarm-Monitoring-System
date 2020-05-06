import React, { Component } from 'react';
import logo from './logo.png'

export default class Navbar extends Component {
    render() {
        return (
            <div>
            <header>
                <div className="navbar navbar-dark bg-dark shadow-sm">
                    <div className="container d-flex justify-content-between">
                        <a href="#" className="navbar-brand d-flex align-items-center">
                        <img src={logo} style={{width:'30px'}} />
                        <strong><span>&nbsp; &nbsp; &nbsp;</span>Fire Alarm System</strong>
                        </a>
                    </div>
                </div>
            </header>
            </div>
        )
    }
}
