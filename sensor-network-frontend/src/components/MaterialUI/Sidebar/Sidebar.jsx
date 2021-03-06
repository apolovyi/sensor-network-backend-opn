import React from 'react';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import { NavLink } from 'react-router-dom';

import withStyles from '@material-ui/core/styles/withStyles';
import Drawer from '@material-ui/core/Drawer';
import Hidden from '@material-ui/core/Hidden';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import sidebarStyle from 'assets/jss/material-dashboard-react/components/sidebarStyle.jsx';

const Sidebar = ({ ...props }) => {
	function activeRoute(routeName) {
		return props.location.pathname.indexOf(routeName) > -1 ? true : false;
	}
	const { classes, color, image, logoText, routes } = props;
	var links = (
		<List className={classes.list}>
			{routes.map((prop, key) => {
				if (prop.redirect) return null;
				//var activePro = ' ';
				var listItemClasses;
				listItemClasses = classNames({
					[' ' + classes[color]]: activeRoute(prop.path)
				});
				const whiteFontClasses = classNames({
					[' ' + classes.whiteFont]: activeRoute(prop.path)
				});
				return (
					<NavLink
						to={prop.path}
						//className={activePro + classes.item}
						activeClassName="active"
						key={key}
					>
						<ListItem button className={classes.itemLink + listItemClasses}>
							<ListItemIcon className={classes.itemIcon + whiteFontClasses}>
								<prop.icon />
							</ListItemIcon>
							<ListItemText
								primary={prop.sidebarName}
								className={classes.itemText + whiteFontClasses}
								disableTypography={true}
							/>
						</ListItem>
					</NavLink>
				);
			})}
		</List>
	);
	var sidebarHeader = (
		<div className={classes.logo}>
			<div className={classes.logoLink}>{logoText}</div>
		</div>
	);
	return (
		<div>
			<Hidden smDown>
				<Drawer
					anchor="left"
					variant="permanent"
					open
					classes={{
						paper: classes.drawerPaper
					}}
				>
					{sidebarHeader}
					<div className={classes.sidebarWrapper}>{links}</div>
					{image !== undefined ? (
						<div
							className={classes.background}
							style={{ backgroundImage: 'url(' + image + ')' }}
						/>
					) : null}
				</Drawer>
			</Hidden>
		</div>
	);
};

Sidebar.propTypes = {
	classes: PropTypes.object.isRequired
};

export default withStyles(sidebarStyle)(Sidebar);
