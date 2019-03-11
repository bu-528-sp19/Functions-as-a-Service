import React, { Component } from 'react';
import './App.css';

import SimpleMap from "./SimpleMap";
import InputGroup from './InputGroup';

class App extends Component {

  state = {
    latitude: "",
    longtitude: "",
    range: "",
    errors: {}
  }

  onChangeHandler = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    });
  }

  render() {

    const { errors } = this.state;

    return (
      <div className="App">
        <div className="container-fluid px-0" id="main">
          <div className="row">
            <div className="col-md-8">
              < SimpleMap />
            </div>
            <div className="col-md-4">
              <div className="container pt-4">
                <h1 className="display-4">Platform</h1>
              </div> 
              <div className="container pt-4 px-5">
                <InputGroup
                  name="latitude"
                  placeholder="42.3401"
                  value={ this.state.latitude }
                  error={ errors.latitude }
                  prepend="Latitude"
                  onChange={ this.onChangeHandler }
                />
              </div>
              <div className="container px-5">
                <InputGroup
                  name="longtitude"
                  placeholder="288.908"
                  value={ this.state.longtitude }
                  error={ errors.longtitude }
                  prepend="Longtitude"
                  onChange={ this.onChangeHandler }
                />
              </div>
              <div className="container px-5">
                <InputGroup
                  name="range"
                  placeholder="5"
                  value={ this.state.range }
                  error={ errors.range }
                  prepend="Range(km)"
                  onChange={ this.onChangeHandler }
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default App;
