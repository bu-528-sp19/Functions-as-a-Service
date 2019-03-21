import React, { Component } from 'react';
import { Provider } from 'react-redux';
import store from './store';
import './App.css';

import SimpleMap from "./SimpleMap";
import Input from "./Input";

class App extends Component {

  render() {
    return (
      <Provider store = { store }>
        <div className="App">
          <div className="container-fluid px-0" id="main">
            <div className="row">
              <div className="col-md-8">
                < SimpleMap />
              </div>
              <div className="col-md-4">
                < Input />
              </div>
            </div>
          </div>
        </div>
      </Provider>
    );
  }
}

export default App;
