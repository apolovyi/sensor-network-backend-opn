import React from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import Grid from '@material-ui/core/Grid';
import GridItem from 'components/Grid/GridItem.jsx';
import dashboardStyle from 'assets/jss/material-dashboard-react/views/dashboardStyle.jsx';
import SavedSensors from '../../components/NEW/Data/SavedSensors';

class Dashboard extends React.Component {
	state = {
		value: 0
	};
	handleChange = (event, value) => {
		this.setState({ value });
	};

	handleChangeIndex = index => {
		this.setState({ value: index });
	};
	render() {
		return <SavedSensors />;
	}
}

Dashboard.propTypes = {
	classes: PropTypes.object.isRequired
};

export default withStyles(dashboardStyle)(Dashboard);
