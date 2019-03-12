import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { googleMapKey } from './config/keys';
import { connect } from 'react-redux';
import { CoordinateStyle } from './CoordinateStyle';
import PropTypes from 'prop-types';

const CoordinateComponent = ({ text }) => <div style={ CoordinateStyle }>{text}</div>;

class SimpleMap extends Component {

  componentDidMount() {
    this.setState({
      center: this.props.map.center,
      zoom: this.props.map.zoom,
      yourCoord: {
        lat: 42.3401,
        lng: 288.9089
      }
    });
  }

  componentWillReceiveProps(nextProps) {

    const newCenter = {
      lat: Number(nextProps.map.center.lat),
      lng: Number(nextProps.map.center.lng)
    }

    this.setState({
      center: newCenter,
      zoom: nextProps.map.zoom,
      yourCoord: newCenter
    });
  }

  state = {
    center: {
      lat: 42.3401,
      lng: 288.9089
    },
    zoom: 14,
    layerTypes: ['TrafficLayer'],
    yourCoord: {
      lat: 42.3401,
      lng: 288.9089
    } 
  }

  render() {
    return (
      <div style={{ height: '100vh', width: '100%' }}>
        <GoogleMapReact
          bootstrapURLKeys={{ key: googleMapKey }}
          center={this.state.center}
          zoom={this.state.zoom}
          defaultLayerTypes={this.props.layerTypes}
        >
          <CoordinateComponent
            {...this.state.yourCoord}
          />
        </GoogleMapReact>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  map: state.map
});

export default connect(mapStateToProps, {})(SimpleMap);