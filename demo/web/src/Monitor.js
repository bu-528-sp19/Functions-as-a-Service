import React, { Component } from 'react';

import Map from "./Map";

export default class Monitor extends Component {
  render() {
    return (
      <div className="monitor">
        <div className="container-fluid px-0" id="main">
          <div className="row">
            <div className="col-md-12">
              < Map />
            </div>
          </div>
        </div>
      </div>
    )
  }
}
