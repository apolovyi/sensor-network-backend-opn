import React from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import dashboardStyle from 'assets/jss/material-dashboard-react/views/dashboardStyle.jsx';
import SavedSensors from '../../components/NEW/Data/SavedSensors';

export default withStyles(dashboardStyle)(
	class Dashboard extends React.Component {
		render() {
			return <SavedSensors />;
		}
	}
);
