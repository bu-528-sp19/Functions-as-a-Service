import React, { Component } from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import store from './store';
import './App.css';

import FindTaxi from './FindTaxi';
import Monitor from './Monitor';

class App extends Component {

  render() {
    return (
      <Provider store = { store }>
        <Router>
          <div className="App">
            <Route exact path="/findTaxi" component={FindTaxi} />
            <Route exact path="/monitor" component={Monitor} />
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
