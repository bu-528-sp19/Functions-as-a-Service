import React, { Component } from 'react';
import InputGroup from './common/InputGroup';
import { connect } from 'react-redux';
import { setPosition, setDefaultPosition } from './actions/mapActions'
import PropTypes from 'prop-types';

class Input extends Component {

  state = {
    latitude: "",
    longtitude: "",
    errors: {}
  }

  onChangeHandler = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    });
  }
  
  queryHandler = () => {
    const data = {
      center: {
        lat: this.state.latitude,
        lng: this.state.longtitude
      },
      zoom: 16,
      yourCoord: {
        lat: this.state.latitude,
        lng: this.state.longtitude
      },
      taxiCoord: [] 
    }

    this.timer = setInterval(() => {
        this.props.setPosition(data);
    },2000);
  }

  resetHandler = () => {
      clearInterval(this.timer);
      this.props.setDefaultPosition();
  }

  render() {

    const { errors } = this.state;

    return (
      <div id="Input">
        <div className="container pt-4">
          <h1 className="display-4">Platform</h1>
        </div> 
        <div className="container px-5 mt-5">
          <InputGroup
            name="latitude"
            placeholder="42.346398"
            value={ this.state.latitude }
            error={ errors.latitude }
            prepend="Latitude"
            onChange={ this.onChangeHandler }
          />
          <InputGroup
            name="longtitude"
            placeholder="288.903679"
            value={ this.state.longtitude }
            error={ errors.longtitude }
            prepend="Longtitude"
            onChange={ this.onChangeHandler }
          />
        </div>
        <div className="container px-5 mt-5">
          <div className="row">
            <div className="col-md-12 mb-4">
              <div className="btn btn-lg btn-info" onClick={ this.queryHandler }>Query</div>
            </div>
            <div className="col-md-12 mb-4">
              <div className="btn btn-lg btn-warning" onClick={ this.resetHandler }>Reset</div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

Input.propTypes = {
  map: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
  map: state.map
});

export default connect(mapStateToProps, { setPosition, setDefaultPosition })(Input);
