import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { googleMapKey } from './config/keys';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import isEmpty from './isEmpty';
import PersonMarker from './PersonMarker';
import DriverMarker from './DriverMarker';
import {findAllPostions} from './actions/mapActions';

class Map extends Component {

  componentWillMount() {
    this.timer = setInterval(() => this.props.findAllPostions(), 8000);
  }

  componentWillReceiveProps(nextProps) {
    this.setState({
      passengerCoord: nextProps.map.passengerCoord,
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
    taxiCoord: [], 
    passengerCoord: []
  }

  render() {

    const passengerMarker = isEmpty(this.state.passengerCoord) ? null : (this.state.passengerCoord.map(coor =>
      <PersonMarker
        key={coor.id}
        text={coor.id}
        lat={coor.lat}
        lng={coor.lng}
      />
    ))
    
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
        { passengerMarker }
        { driverMarker }
        </GoogleMapReact>
      </div>
    );
  }
}

Map.propTypes = {
  map: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
  map: state.map
});

export default connect(mapStateToProps, { findAllPostions })(Map);