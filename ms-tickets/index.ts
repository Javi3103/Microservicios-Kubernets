import { ApolloServer } from '@apollo/server';
import { startStandaloneServer } from '@apollo/server/standalone';
import { typeDefs } from './src/typeDefs/schemas';
import { resolvers } from './src/resolvers';
import { initializeDatabase } from './src/utils/database';
import * as dotenv from 'dotenv';

/**
 * Función principal que inicia el servidor GraphQL.
 * Configura Apollo Server, inicializa la base de datos y arranca el servidor.
 */
const startServer = async (): Promise<void> => {
  // 1. Cargar variables de entorno
  dotenv.config();
  
  // 2. Validar variables de entorno requeridas
  const zoneUrl = process.env.ZONE_SERVICE_URL;
  const personaUrl = process.env.PERSONA_SERVICE_URL || process.env.USER_SERVICE_URL;

  if (!zoneUrl) {
    throw new Error('Variable de entorno requerida no encontrada: ZONE_SERVICE_URL');
  }
  if (!personaUrl) {
    throw new Error('Variable de entorno requerida no encontrada: PERSONA_SERVICE_URL o USER_SERVICE_URL');
  }

  // Normalizar para el resto de la app y logs
  process.env.PERSONA_SERVICE_URL = personaUrl;

  // 3. Inicializar conexión a la base de datos
  console.log('🔄 Inicializando conexión a la base de datos...');
  await initializeDatabase();

  // 4. Configurar Apollo Server
  const server = new ApolloServer({
    typeDefs,
    resolvers,
    introspection: process.env.NODE_ENV !== 'production', // Habilitar introspection en desarrollo
    formatError: (error) => {
      // Formatear errores para respuesta más clara
      console.error('GraphQL Error:', error);
      return {
        message: error.message,
        extensions: {
          code: error.extensions?.code || 'INTERNAL_SERVER_ERROR',
        },
      };
    },
  });

  // 5. Iniciar servidor en modo standalone
  const port = parseInt(process.env.PORT || '4000');
  const { url } = await startStandaloneServer(server, {
    listen: { port },
    context: async ({ req }) => ({
      // Aquí se puede agregar autenticación/autorización
      token: req.headers.authorization || '',
    }),
  });

  // 6. Mensaje de inicio exitoso
  console.log(`
🚀 Servidor GraphQL iniciado exitosamente!
📡 URL del servidor: ${url}
🗄️  Base de datos: ${process.env.DB_NAME}
🔌 Microservicio Zonas: ${process.env.ZONE_SERVICE_URL}
👥 Microservicio Personas: ${process.env.PERSONA_SERVICE_URL}

📚 Endpoints disponibles:
   - GraphQL API: ${url}
   - Playground GraphQL: ${url} (disponible en desarrollo)

📝 Consultas de ejemplo:
   query {
     tickets {
       codigoTicket
       personaNombre
       vehiculoPlaca
       estado
     }
   }

   mutation {
     emitirTicket(
       personaIdentificacion: "1712345678"
       vehiculoPlaca: "ABC-123"
     ) {
       codigoTicket
       espacioCodigo
     }
   }
  `);
};

/**
 * Manejo de errores no capturados
 */
process.on('unhandledRejection', (error) => {
  console.error('❌ Error no manejado:', error);
  process.exit(1);
});

process.on('uncaughtException', (error) => {
  console.error('❌ Excepción no capturada:', error);
  process.exit(1);
});

// Iniciar el servidor
startServer().catch((error) => {
  console.error('❌ Error fatal al iniciar el servidor:', error);
  process.exit(1);
});