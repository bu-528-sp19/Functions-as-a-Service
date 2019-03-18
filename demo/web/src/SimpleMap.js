import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { googleMapKey } from './config/keys';
import { connect } from 'react-redux';
import { CoordinateStyle } from './CoordinateStyle';
import { CoordinateStyle2 } from './CoordinateStyle2';
import PropTypes from 'prop-types';
import isEmpty from './isEmpty';

const Coordinate1Component = ({ text }) => <div style={ CoordinateStyle }>{text}</div>;
const Coordinate2Component = ({ text }) => <div style={ CoordinateStyle2 }>{text}</div>

class SimpleMap extends Component {

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
      taxiCoord: nextProps.map.taxiCoord
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

    const yourMarker = isEmpty(this.state.yourCoord) ? null : (<Coordinate1Component
      {...this.state.yourCoord}
    />)

    const driverMarker = isEmpty(this.state.taxiCoord) ? null : (this.state.taxiCoord.map(coor => 
        <Coordinate2Component
          key = {coor.id}
          {...coor}
        />
      ))

    return (
      <div style={{ height: '100vh', width: '100%' }}>
        <GoogleMapReact
          bootstrapURLKeys={{ key: googleMapKey }}
          center={this.state.center}
          zoom={this.state.zoom}
          defaultLayerTypes={this.props.layerTypes}
        >
        { yourMarker }
        { driverMarker }
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