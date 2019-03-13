import { SET_CURRENT_POS } from '../actions/types';

const initialState = {
  center: {},
  zoom: 14,
  yourCoord: {},
  taxiCoord: []
};

export default function(state = initialState, action) {
  switch(action.type) {
    case SET_CURRENT_POS:
      return {
        center: action.payload.center,
        zoom: action.payload.zoom,
        yourCoord: action.payload.yourCoord,
        taxiCoord: action.payload.taxiCoord
      };
    default:
      return state;
  }
}