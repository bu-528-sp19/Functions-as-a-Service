import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

const Wrapper = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  width: 18px;
  height: 18px;
  background-color: #FF0000;
  border: 2px solid #FFFFFF;
  border-radius: 100%;
  user-select: none;
  transform: translate(-50%, -50%);
  cursor: ${props => (props.onClick ? 'pointer' : 'default')};
  &:hover {
    z-index: 1;
  }
`;

const PersonMarker = props => (
  <Wrapper
    alt={props.text}
    {...props.onClick ? { onClick: props.onClick } : {}}
  />
);

PersonMarker.defaultProps = {
  onClick: null,
};

PersonMarker.propTypes = {
  onClick: PropTypes.func,
  text: PropTypes.string.isRequired,
};

export default PersonMarker;