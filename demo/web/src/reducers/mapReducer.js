import { SET_CURRENT_POS } from '../actions/types';

const initialState = {
  center: {},
  zoom: 14
};

export default function(state = initialState, action) {
  switch(action.type) {
    case SET_CURRENT_POS:
      return {
        center: action.payload.center,
        zoom: action.payload.zoom
      };
    default:
      return state;
  }
}