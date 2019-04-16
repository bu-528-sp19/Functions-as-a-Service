import React, { Component } from 'react'
import SimpleMap from "./SimpleMap";
import Input from "./Input";

export default class FindTaxi extends Component {
  render() {
    return (
      <div className="findtaxi">
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
    )
  }
}
