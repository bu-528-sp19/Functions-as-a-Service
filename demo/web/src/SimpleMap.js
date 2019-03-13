import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { googleMapKey } from './config/keys';
import { connect } from 'react-redux';
import { CoordinateStyle } from './CoordinateStyle';
import PropTypes from 'prop-types';
import isEmpty from './isEmpty';

const CoordinateComponent = ({ text }) => <div style={ CoordinateStyle }>{text}</div>;

class SimpleMap extends Component {

  componentDidMount() {
    this.setState({
      center: this.state.center,
      zoom: this.state.zoom,
      yourCoord: {}
    });
  }

  componentWillReceiveProps(nextProps) {

    const newCenter = {
      lat: Number(nextProps.map.center.lat),
      lng: Number(nextProps.map.center.lng)
    }

    const newYourCoord = isEmpty(nextProps.map.yourCoord) ? null : {
      lat: Number(nextProps.map.yourCoord.lat),
      lng: Number(nextProps.map.yourCoord.lng)
    }

    this.setState({
      center: newCenter,
      zoom: nextProps.map.zoom,
      yourCoord: newYourCoord,
      taxiCoord: []
    });
  }

  state = {
    center: {
      lat: 42.3401,
      lng: 288.9089
    },
    zoom: 14,
    layerTypes: ['TrafficLayer'],
    yourCoord: {},
    taxiCoord: [] 
  }

  render() {

    const marker = isEmpty(this.state.yourCoord) ? null : (<CoordinateComponent
      {...this.state.yourCoord}
    />)

    return (
      <div style={{ height: '100vh', width: '100%' }}>
        <GoogleMapReact
          bootstrapURLKeys={{ key: googleMapKey }}
          center={this.state.center}
          zoom={this.state.zoom}
          defaultLayerTypes={this.props.layerTypes}
        >
        { marker }
        </GoogleMapReact>
      </div>
    );
  }
}

SimpleMap.propTypes = {
  map: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
  map: state.map
});

export default connect(mapStateToProps, {})(SimpleMap);