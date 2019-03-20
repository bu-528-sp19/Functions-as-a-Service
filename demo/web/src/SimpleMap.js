import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { googleMapKey } from './config/keys';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import isEmpty from './isEmpty';
import PersonMarker from './PersonMarker';
import DriverMarker from './DriverMarker';

class SimpleMap extends Component {

  componentWillReceiveProps(nextProps) {

    const newCenter = {
      lat: Number(nextProps.map.center.lat),
      lng: 360 + Number(nextProps.map.center.lng)
    }

    const newYourCoord = isEmpty(nextProps.map.yourCoord) ? null : {
      lat: Number(nextProps.map.yourCoord.lat),
      lng: 360 + Number(nextProps.map.yourCoord.lng)
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
      lng: -71.092
    },
    zoom: 14,
    layerTypes: ['TrafficLayer'],
    yourCoord: {},
    taxiCoord: [] 
  }

  render() {

    const yourMarker = isEmpty(this.state.yourCoord) ? null : (
      <PersonMarker
        text={"You"}
        lat={this.state.yourCoord.lat}
        lng={this.state.yourCoord.lng}
      />
    )
    
    const driverMarker = isEmpty(this.state.taxiCoord) ? null : (this.state.taxiCoord.map(coor =>
        <DriverMarker
          key={coor.id}
          text={coor.id}
          lat={coor.lat}
          lng={coor.lng}
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