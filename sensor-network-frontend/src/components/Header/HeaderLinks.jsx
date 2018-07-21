import React from 'react';
import withStyles from '@material-ui/core/styles/withStyles';

import Hidden from '@material-ui/core/Hidden';
import Person from '@material-ui/icons/Person';

import Button from 'components/CustomButtons/Button.jsx';

import headerLinksStyle from 'assets/jss/material-dashboard-react/components/headerLinksStyle';

class HeaderLinks extends React.Component {
	state = {
		open: false
	};
	handleClick = () => {
		this.setState({ open: !this.state.open });
	};

	handleClose = () => {
		this.setState({ open: false });
	};
	render() {
		const { classes } = this.props;
		const { open } = this.state;
		return (
			<div>
				<Button
					color={window.innerWidth > 959 ? 'transparent' : 'white'}
					justIcon={window.innerWidth > 959}
					simple={!(window.innerWidth > 959)}
					aria-label="Person"
					className={classes.buttonLink}
				>
					<Person className={classes.icons} />
					<Hidden mdUp>
						<p className={classes.linkText}>Profile</p>
					</Hidden>
				</Button>
			</div>
		);
	}
}

export default withStyles(headerLinksStyle)(HeaderLinks);
