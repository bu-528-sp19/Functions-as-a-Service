import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { googleMapKey } from './config/keys';

const AnyReactComponent = ({ text }) => <div>{text}</div>;

class SimpleMap extends Component {

  static defaultProps = {
    center: {
      lat: 42.3401,
      lng: 288.9089
    },
    zoom: 14,
    layerTypes: ['TrafficLayer']
  };

  render() {
    return (
      <div style={{ height: '100vh', width: '100%' }}>
        <GoogleMapReact
          bootstrapURLKeys={{ key: googleMapKey }}
          defaultCenter={this.props.center}
          defaultZoom={this.props.zoom}
          defaultLayerTypes={this.props.layerTypes}
        >
          <AnyReactComponent
            lat={59.955413}
            lng={30.337844}
            text="My Marker"
          />
        </GoogleMapReact>
      </div>
    );
  }
}

export default SimpleMap;