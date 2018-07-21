import Dashboard from '@material-ui/icons/Dashboard';
import SettingsPage from 'views/Settings/Settings.jsx';
import DashboardPage from 'views/Dashboard/Dashboard.jsx';

const dashboardRoutes = [
	{
		path: '/settings',
		sidebarName: 'Settings',
		navbarName: 'MQTT Settings',
		icon: Dashboard,
		component: SettingsPage
	},
	{
		path: '/dashboard',
		sidebarName: 'Dashboard',
		navbarName: 'Sensor Dashboard',
		icon: Dashboard,
		component: DashboardPage
	},
	{ redirect: true, path: '/', to: '/dashboard', navbarName: 'Redirect' }
];

export default dashboardRoutes;
