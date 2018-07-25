import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
//import PerfectScrollbar from 'perfect-scrollbar';
//import 'perfect-scrollbar/css/perfect-scrollbar.css';
import withStyles from '@material-ui/core/styles/withStyles';
import Sidebar from 'components/MaterialUI/Sidebar/Sidebar.jsx';
import dashboardRoutes from 'routes/dashboard.jsx';
import dashboardStyle from 'assets/jss/material-dashboard-react/layouts/dashboardStyle.jsx';
import image from 'assets/img/black.jpg';

const switchRoutes = (
	<Switch>
		{dashboardRoutes.map((prop, key) => {
			if (prop.redirect)
				return <Redirect from={prop.path} to={prop.to} key={key} />;
			return <Route path={prop.path} component={prop.component} key={key} />;
		})}
	</Switch>
);

export default withStyles(dashboardStyle)(
	class App extends React.Component {
		/* state = {
			mobileOpen: false
		};
		handleDrawerToggle = () => {
			this.setState({ mobileOpen: !this.state.mobileOpen });
		};
		componentDidMount() {
			if (navigator.platform.indexOf('Win') > -1) {
				const ps = new PerfectScrollbar(this.refs.mainPanel);
			}
		}
		componentDidUpdate(e) {
			if (e.history.location.pathname !== e.location.pathname) {
				this.refs.mainPanel.scrollTop = 0;
				if (this.state.mobileOpen) {
					this.setState({ mobileOpen: false });
				}
			}
		} */
		render() {
			const { classes, ...rest } = this.props;
			return (
				<div className={classes.wrapper}>
					<Sidebar
						routes={dashboardRoutes}
						logoText={'TH Sensor Network'}
						image={image}
						//handleDrawerToggle={this.handleDrawerToggle}
						//open={this.state.mobileOpen}
						color="purple"
						{...rest}
					/>
					<div className={classes.mainPanel} ref="mainPanel">
						<div className={classes.content}>
							<div className={classes.container}>{switchRoutes}</div>
						</div>
					</div>
				</div>
			);
		}
	}
);
