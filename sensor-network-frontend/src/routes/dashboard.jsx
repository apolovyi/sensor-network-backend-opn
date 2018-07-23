import Dashboard from '@material-ui/icons/Dashboard';
import Settings from '@material-ui/icons/Settings';
import SettingsPage from 'views/Settings/Settings.jsx';
import DashboardPage from 'views/Dashboard/Dashboard.jsx';

const dashboardRoutes = [
	{
		path: '/settings',
		sidebarName: 'Settings',
		navbarName: 'MQTT Settings',
		icon: Settings,
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
